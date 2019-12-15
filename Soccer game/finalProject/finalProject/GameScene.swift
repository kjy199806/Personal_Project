//
//  GameScene.swift
//  finalProject
//
//  Created by Junyoung Kim on 2019-07-30.
//  Copyright © 2019 Junyoung Kim. All rights reserved.
//

import SpriteKit
import GameplayKit
import CoreData

class GameScene: SKScene, SKPhysicsContactDelegate {

    // Setting category
    var mainCharacterCategory: UInt32 = 0x1 << 1  // = 2
    var enemyCharacterCategory: UInt32 = 0x1 << 2 // = 4
    var leftWallCategory: UInt32 = 0x1 << 3       // = 8
    var rightWallCategory: UInt32 = 0x1 << 4      // = 16
    var ballCategory: UInt32 = 0x1 << 5           // = 32
    var goalWallCategory: UInt32 = 0x1 << 6       // = 64
    var notGoalwallCategory: UInt32 = 0x1 << 7    // = 128
    var animationTimer: Timer?
    var ballAnimationTimer: Timer?
    var user = String()
    
    let bounds = UIScreen.main.bounds
    var width: CGFloat?
    var height : CGFloat?
    var unit_vector : CGVector?
    var unitVector: simd_double2?
    var dir: CGVector?
    var mainCharacter: SKSpriteNode?
    var enemy1: SKSpriteNode?
    var enemy2: SKSpriteNode?
    var enemy3: SKSpriteNode?
    var wallLeft: SKSpriteNode?
    var wallRight: SKSpriteNode?
    var soccerBall: SKSpriteNode?
    var goalWall: SKSpriteNode?
    var notGoalWallLeft: SKSpriteNode?
    var notGoalWallRight: SKSpriteNode?
    
    var scoreLabel: SKLabelNode!
    var lifeLabel: SKLabelNode!
    
//    var tap: UITapGestureRecognizer?
    var pan: UIPanGestureRecognizer?
    let tapRec = UITapGestureRecognizer()

    var mainChaPositionX : Int?
    var mainChaPositionY : Int?
    var startPoint = CGPoint(x:0,y:0)
    var endPoint = CGPoint(x:0,y:0)
    var iterator = 1
    
    var score = 0
    var life = 3
    var myTarget : CGPoint?
    
    override func didMove(to view: SKView) {
        physicsWorld.contactDelegate = self

        width = bounds.size.width
        height = bounds.size.height
        spawnMainCharacter()
        spawnEnemyCharacter()
        spawnWall()
        spawnSoccerBall()
//        tap = UITapGestureRecognizer(target: self, action: #selector(handleTap))
//        self.view!.addGestureRecognizer(tap!)
        pan = UIPanGestureRecognizer(target: self, action: #selector(handlePan))
        self.view!.addGestureRecognizer(pan!)
        tapRec.addTarget(self, action:#selector(GameScene.tappedView(_:) ))
        tapRec.numberOfTouchesRequired = 1
        tapRec.numberOfTapsRequired = 1
        self.view!.addGestureRecognizer(tapRec)
        // Setting labels
        scoreLabel = childNode(withName: "scoreLabel") as? SKLabelNode
        lifeLabel = childNode(withName: "lifeLabel") as? SKLabelNode
    }
    
    @objc func tappedView(_ sender:UITapGestureRecognizer) {
        
        let point:CGPoint = sender.location(in: self.view)
        
        print("Single tap")
        
        print(point)
        
        if ((ballAnimationTimer) != nil){
            ballAnimationTimer?.invalidate()
        }
        myTarget = CGPoint(x: point.x, y: point.y)
        
        myTarget!.x = myTarget!.x - width!/2
        myTarget!.y = myTarget!.y + height!/2
        print(height!/2)
        
        ballAnimationTimer = Timer.scheduledTimer(timeInterval: TimeInterval(0.1), target: self, selector: #selector(moveBall), userInfo: nil, repeats: true)
        
        
    }
    
    @objc func moveBall(){

        print(myTarget!.x)
        print(myTarget!.y)
        var current_Pos = soccerBall?.position
        var target_vector = CGVector(dx: myTarget!.x - current_Pos!.x, dy: myTarget!.y - current_Pos!.y)
        
        var length = sqrt(pow(target_vector.dx, 2) + pow(target_vector.dy, 2))
        
        unit_vector = CGVector(dx: target_vector.dx / length, dy: target_vector.dy / length)
        soccerBall?.position.x += unit_vector!.dx * 10
        soccerBall?.position.y += unit_vector!.dy * 10
        
        
    }
    
    @objc func handleTap(touch: UITapGestureRecognizer){
        print("Width = " , width)
        print("Height = " , height)
        // Stop timer
        if ((ballAnimationTimer) != nil){
            ballAnimationTimer?.invalidate()
        }
        let point = touch.location(in: view)
        myTarget = point
        ballAnimationTimer = Timer.scheduledTimer(timeInterval: TimeInterval(0.1), target: self, selector: #selector(moveBall), userInfo: nil, repeats: true)
        
    }
    
    
    @objc func handlePan(pan: UIPanGestureRecognizer){
        
        // Stop timer
        if ((ballAnimationTimer) != nil){
            ballAnimationTimer?.invalidate()
        }
        
        if pan.state == UIGestureRecognizer.State.began {

            startPoint = pan.location(in: view)
        
        } else if pan.state == UIGestureRecognizer.State.ended {

            endPoint = pan.location(in: view)
        }
        dir = CGVector(dx: (endPoint.x - startPoint.x), dy: (endPoint.y - startPoint.y))
   
    
        ballAnimationTimer = Timer.scheduledTimer(timeInterval: TimeInterval(0.01), target: self, selector: #selector(panBall), userInfo: nil, repeats: true)

    }
    
    @objc func panBall(){
        if ((dir) != nil){
            unitVector = simd_normalize(simd_double2(x:Double(dir!.dx), y: Double(dir!.dy)))
            soccerBall?.position.x += CGFloat(unitVector!.x) * 10
            soccerBall?.position.y -= CGFloat(unitVector!.y) * 10
        }
    }
    
   
    
    
    func spawnSoccerBall(){
        soccerBall = SKSpriteNode(imageNamed: "soccerBall")
        soccerBall?.size = CGSize(width: 30, height: 30)
        soccerBall?.position = CGPoint(x: CGFloat(mainChaPositionX!), y: CGFloat(mainChaPositionY! + 30))
        soccerBall?.physicsBody = SKPhysicsBody(rectangleOf: (soccerBall?.size)!)
        soccerBall?.physicsBody?.affectedByGravity = false
        soccerBall?.physicsBody?.allowsRotation = false
        soccerBall?.physicsBody?.categoryBitMask = ballCategory
        soccerBall?.physicsBody?.contactTestBitMask = goalWallCategory | notGoalwallCategory
        addChild(soccerBall!)
    }
    
    func spawnMainCharacter(){
        // Create main character
        mainCharacter = SKSpriteNode(imageNamed: "frame-1")
        var fx = Int32(arc4random_uniform(200))-100
        var fy = Int(-(height!/2))
        mainChaPositionX = Int(fx)
        mainChaPositionY = Int(fy)
        mainCharacter?.position = CGPoint(x: CGFloat(fx), y: CGFloat(fy))
        mainCharacter?.size = CGSize(width: 60, height: 80)
        mainCharacter?.physicsBody = SKPhysicsBody(rectangleOf: (mainCharacter?.size)!)
        mainCharacter?.physicsBody?.categoryBitMask = mainCharacterCategory
        mainCharacter?.physicsBody?.allowsRotation = false
        mainCharacter?.physicsBody?.affectedByGravity = false
        mainCharacter?.physicsBody?.collisionBitMask = 0
        addChild(mainCharacter!)
        
    }
    
    func spawnEnemyCharacter(){
        
        // Create enemy1 character
        enemy1 = SKSpriteNode(imageNamed: "frame-1")
        var fx = Int32(arc4random_uniform(200))-100
        var fy = Int32(arc4random_uniform(100)) + 300
        enemy1?.position = CGPoint(x: CGFloat(fx), y: CGFloat(fy))
        enemy1?.size = CGSize(width: 60, height: 80)
        enemy1?.physicsBody = SKPhysicsBody(rectangleOf: (enemy1?.size)!)
        enemy1?.physicsBody?.linearDamping = 0.0
        enemy1?.physicsBody?.categoryBitMask = enemyCharacterCategory
        enemy1?.physicsBody?.allowsRotation = false
        enemy1?.physicsBody?.affectedByGravity = false
        enemy1?.physicsBody?.collisionBitMask = leftWallCategory | rightWallCategory
        addChild(enemy1!)
  
        // Create enemy2 character
        enemy2 = SKSpriteNode(imageNamed: "frame-1")
        fx = Int32(arc4random_uniform(200))-100
        fy = Int32(arc4random_uniform(100)) + 200
        enemy2?.position = CGPoint(x: CGFloat(fx), y: CGFloat(fy))
        enemy2?.size = CGSize(width: 60, height: 80)
        enemy2?.physicsBody = SKPhysicsBody(rectangleOf: (enemy2?.size)!)
        enemy2?.physicsBody?.linearDamping = 0.0
        enemy2?.physicsBody?.categoryBitMask = enemyCharacterCategory
        enemy2?.physicsBody?.allowsRotation = false
        enemy2?.physicsBody?.affectedByGravity = false
        enemy2?.physicsBody?.collisionBitMask = leftWallCategory | rightWallCategory
        addChild(enemy2!)

        // Create enemy3 character
        enemy3 = SKSpriteNode(imageNamed: "frame-1")
        fx = Int32(arc4random_uniform(200))-100
        fy = Int32(arc4random_uniform(100)) + 50
        
        enemy3?.position = CGPoint(x: CGFloat(fx), y: CGFloat(fy))
        enemy3?.size = CGSize(width: 60, height: 80)
        enemy3?.physicsBody = SKPhysicsBody(rectangleOf: enemy3!.size)
        enemy3?.physicsBody?.linearDamping = 0.0
        enemy3?.physicsBody?.categoryBitMask = enemyCharacterCategory
        enemy3?.physicsBody?.allowsRotation = false
        enemy3?.physicsBody?.affectedByGravity = false
        enemy3?.physicsBody?.collisionBitMask = leftWallCategory | rightWallCategory
        addChild(enemy3!)

    }
    
    func spawnWall(){
        // Setting Bouncy wall
        wallLeft = childNode(withName: "leftWall") as! SKSpriteNode
        wallRight = childNode(withName: "rightWall") as! SKSpriteNode
        wallLeft?.physicsBody?.categoryBitMask = leftWallCategory
        wallRight?.physicsBody?.categoryBitMask = rightWallCategory
        wallLeft?.physicsBody?.collisionBitMask = enemyCharacterCategory
        wallRight?.physicsBody?.collisionBitMask = enemyCharacterCategory
        
        // Setting Goal wall
        goalWall = childNode(withName: "goalWall") as! SKSpriteNode
        goalWall?.physicsBody?.categoryBitMask = goalWallCategory
        goalWall?.physicsBody?.collisionBitMask = 0
        goalWall?.physicsBody?.contactTestBitMask = ballCategory
        
        // Setting not Goal wall
        notGoalWallLeft = childNode(withName: "notGoalWallLeft") as! SKSpriteNode
        notGoalWallRight = childNode(withName: "notGoalWallRight") as! SKSpriteNode
        notGoalWallLeft?.physicsBody?.categoryBitMask = notGoalwallCategory
        notGoalWallRight?.physicsBody?.categoryBitMask = notGoalwallCategory
        notGoalWallRight?.physicsBody?.collisionBitMask = 0
        notGoalWallLeft?.physicsBody?.collisionBitMask = 0
        notGoalWallLeft?.physicsBody?.contactTestBitMask = ballCategory
        notGoalWallRight?.physicsBody?.contactTestBitMask = ballCategory

    }
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        animationTimer = Timer.scheduledTimer(timeInterval: TimeInterval(0.1), target: self, selector: #selector(moveAnimation), userInfo: nil, repeats: true)
        
        // Set speed for each enemies
        var fx = CGFloat(arc4random_uniform(2000)) - 1000
        enemy1?.physicsBody?.applyForce(CGVector(dx: fx, dy: 0))
        
        fx = CGFloat(arc4random_uniform(2000)) - 1000
        enemy2?.physicsBody?.applyForce(CGVector(dx: fx, dy: 0))

        fx = CGFloat(arc4random_uniform(2000)) - 1000
        enemy3?.physicsBody?.applyForce(CGVector(dx: fx, dy: 0))

        // Set Contact Test Bit Mask
        enemy1?.physicsBody?.contactTestBitMask = leftWallCategory | rightWallCategory
        enemy2?.physicsBody?.contactTestBitMask = leftWallCategory | rightWallCategory
        enemy3?.physicsBody?.contactTestBitMask = leftWallCategory | rightWallCategory
        
        wallLeft?.physicsBody?.contactTestBitMask = enemyCharacterCategory
        wallRight?.physicsBody?.contactTestBitMask = enemyCharacterCategory
    }
    
    @objc func moveAnimation(){
        mainCharacter?.texture = SKTexture(imageNamed: "frame-\(iterator).png")
        enemy1?.texture = SKTexture(imageNamed: "frame-\(iterator).png")
        enemy2?.texture = SKTexture(imageNamed: "frame-\(iterator).png")
        enemy3?.texture = SKTexture(imageNamed: "frame-\(iterator).png")
        iterator += 1
        if (iterator == 5){
            iterator = 1
        }
        
    }
    
//    var mainCharacterCategory: UInt32 = 0x1 << 1  // = 2
//    var enemyCharacterCategory: UInt32 = 0x1 << 2 // = 4
//    var leftWallCategory: UInt32 = 0x1 << 3       // = 8
//    var rightWallCategory: UInt32 = 0x1 << 4      // = 16
//    var ballCategory: UInt32 = 0x1 << 5           // = 32
//    var goalWallCategory: UInt32 = 0x1 << 6       // = 64
//    var notGoalwallCategory: UInt32 = 0x1 << 7    // = 128

    func didBegin(_ contact: SKPhysicsContact) {

        // Contact between ball and not goal wall
        if ((contact.bodyA.categoryBitMask == 32 && contact.bodyB.categoryBitMask == 128) ||
            (contact.bodyA.categoryBitMask == 128 && contact.bodyB.categoryBitMask == 32) ){
            
            // Remove ball
            if (contact.bodyA.categoryBitMask == 32){
                contact.bodyA.node?.removeFromParent()
                ballAnimationTimer?.invalidate()
                spawnSoccerBall()
//                self.view!.addGestureRecognizer(tap!)
                self.view!.addGestureRecognizer(pan!)
                life -= 1
                lifeLabel.text = "Life: \(life)"
            }
            else if (contact.bodyB.categoryBitMask == 32){
                contact.bodyB.node?.removeFromParent()
                ballAnimationTimer?.invalidate()
                spawnSoccerBall()
//                self.view!.addGestureRecognizer(tap!)
                self.view!.addGestureRecognizer(pan!)
                life -= 1
                lifeLabel.text = "Life: \(life)"

            }
            
            // End game
            if (life == 0){
                updateScore()
                let transition = SKTransition.flipVertical(withDuration: 0.5)
                let scoreScene = ScoreScene(fileNamed: "ScoreScene")
                scoreScene?.scaleMode = .aspectFill
                scoreScene?.username = user
                self.view?.presentScene(scoreScene!, transition: transition)
                
            }
        }
        // Contact between ball and goal wall
        if ((contact.bodyA.categoryBitMask == 32 && contact.bodyB.categoryBitMask == 64) ||
            (contact.bodyA.categoryBitMask == 64 && contact.bodyB.categoryBitMask == 32) ){
            
            // Remove ball
            if (contact.bodyA.categoryBitMask == 32){
                contact.bodyA.node?.removeFromParent()
                ballAnimationTimer?.invalidate()
                spawnSoccerBall()
                self.view!.addGestureRecognizer(pan!)
//                self.view!.addGestureRecognizer(tap!)
                score += 1
                scoreLabel.text = "Score: \(score)"

            }
            else if (contact.bodyB.categoryBitMask == 32){
                contact.bodyB.node?.removeFromParent()
                ballAnimationTimer?.invalidate()
                spawnSoccerBall()
                self.view!.addGestureRecognizer(pan!)
//                self.view!.addGestureRecognizer(tap!)
                score += 1
                scoreLabel.text = "Score: \(score)"

            }
            
        }
        
        // Contact between enemy and left wall
        if ((contact.bodyA.categoryBitMask == 4 && contact.bodyB.categoryBitMask == 8) ||
            (contact.bodyA.categoryBitMask == 8 && contact.bodyB.categoryBitMask == 4) ){
            
           
            if (contact.bodyA.categoryBitMask == 4){
                var fx = CGFloat(arc4random_uniform(200)) + 40000
                contact.bodyA.applyForce(CGVector(dx: fx, dy: 0))
            }
            else if (contact.bodyB.categoryBitMask == 4) {
                var fx = CGFloat(arc4random_uniform(200)) + 40000
                contact.bodyB.applyForce(CGVector(dx: fx, dy: 0))
            }
            
        }
        
        // Contact between enemy and right wall
        if ((contact.bodyA.categoryBitMask == 4 && contact.bodyB.categoryBitMask == 16) ||
            (contact.bodyA.categoryBitMask == 16 && contact.bodyB.categoryBitMask == 4) ){
            
            if (contact.bodyA.categoryBitMask == 4){
                var fx = CGFloat(arc4random_uniform(200)) - 42000
                contact.bodyA.applyForce(CGVector(dx: fx, dy: 0))
            }
            else if (contact.bodyB.categoryBitMask == 4) {
                var fx = CGFloat(arc4random_uniform(200)) - 42000
                contact.bodyB.applyForce(CGVector(dx: fx, dy: 0))
            }
            
        }
        
    }
    
    func updateScore(){
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        let context = appDelegate.persistentContainer.viewContext
        
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Account")
        
        request.predicate = NSPredicate(format: "username = %@", user)
        do {
            let result = try context.fetch(request)
            var scoreData = 0
            for data in result as! [NSManagedObject]{
                 scoreData = data.value(forKey: "score") as! Int
            }
            if (scoreData < score){
                let objectUpdate = result[0] as! NSManagedObject
                objectUpdate.setValue(score, forKey: "score")
                do {
                    try context.save()
                } catch {
                    print(error)
                }
            }

        } catch {
            print("Failed")
        }
        
        
    }
    
    override func update(_ currentTime: TimeInterval) {
        // Called before each frame is rendered
    }
}
