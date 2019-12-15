//
//  ScoreScene.swift
//  finalProject
//
//  Created by Junyoung Kim on 2019-08-01.
//  Copyright © 2019 Junyoung Kim. All rights reserved.
//

import Foundation
import SpriteKit
import GameplayKit
import CoreData

class ScoreScene: SKScene {
    
    var homeButtonNode: SKSpriteNode!
    var topScoreLabel: SKLabelNode!
    var topUserLabel: SKLabelNode!
    var topScoreString = ""
    var topUserString = ""
    
    var username = String()
    struct ScoreRow {
        var username = ""
        var score = 0
    }
    var topTen: [ScoreRow] = []
    override func didMove(to view: SKView) {
        homeButtonNode = self.childNode(withName: "homeButton") as! SKSpriteNode
      
        // Setting labels
        topScoreLabel = childNode(withName: "topScore") as? SKLabelNode
        topUserLabel = childNode(withName: "topUser") as? SKLabelNode
        getTopTen()
    }
    
    func getTopTen(){
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        let context = appDelegate.persistentContainer.viewContext
        
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Account")
        request.returnsObjectsAsFaults = false
        do {
            let result = try context.fetch(request)
            for data in result as! [NSManagedObject] {
                var newRow = ScoreRow(username: data.value(forKey: "username") as! String, score: data.value(forKey: "score") as! Int)
                if (newRow.username != ""){
                    topTen.append(newRow)
                }
            }
        } catch {
            print("Failed")
        }
        topTen.sort(by: sortByScore)
        var maxLength = 9
        
        if (topTen.count - 1 < 9){
            maxLength = topTen.count - 1
        }
        
        for idx in 0...maxLength {
            topScoreString += String(topTen[idx].score) + "\n"
            topUserString += topTen[idx].username + "\n"
        }

        topScoreLabel.text = topScoreString
        topUserLabel.text = topUserString
    }
    
    func sortByScore(data1:ScoreRow, data2:ScoreRow) -> Bool {
        return data1.score >= data2.score
    }
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        let touch = touches.first
        
        if let location = touch?.location(in: self){
            let nodesArray = self.nodes(at: location)
            
            if nodesArray.first?.name == "homeButton" {
                let transition = SKTransition.flipVertical(withDuration: 0.5)
                let mainScene = MainMenu(fileNamed: "Menu")
                mainScene?.scaleMode = .aspectFill
                mainScene?.username = username
                self.view?.presentScene(mainScene!, transition: transition)
            }
        }
    }
}
