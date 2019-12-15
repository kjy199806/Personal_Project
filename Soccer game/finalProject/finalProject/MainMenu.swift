//
//  MainMenu.swift
//  finalProject
//
//  Created by Junyoung Kim on 2019-07-30.
//  Copyright © 2019 Junyoung Kim. All rights reserved.
//

import Foundation
import SpriteKit

class MainMenu: SKScene {

    
    var newGameButtonNode: SKSpriteNode!
    var topTenPlayerButtonNode: SKSpriteNode!
    var exitButtonNode: SKSpriteNode!
    
    override func didMove(to view: SKView){
        newGameButtonNode = self.childNode(withName: "newGameButton") as! SKSpriteNode
        topTenPlayerButtonNode = self.childNode(withName: "topTenPlayerButton") as! SKSpriteNode
        exitButtonNode = self.childNode(withName: "exitButton") as! SKSpriteNode
    }

    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        let touch = touches.first
        
        if let location = touch?.location(in: self){
            let nodesArray = self.nodes(at: location)
            
            if nodesArray.first?.name == "newGameButton" {
                let transition = SKTransition.flipVertical(withDuration: 0.5)
                let gameScene = GameScene(fileNamed: "GameScene")
                gameScene?.scaleMode = .aspectFill
                self.view?.presentScene(gameScene!, transition: transition)
            }
//            else if nodesArray.first?.name == "topTenPlayerButton" {
//                let transition = SKTransition.flipVertical(withDuration: 0.5)
//                let scoreScene = ScoreScene(size: self.size)
//                self.view?.presentScene(scoreScene, transition: transition)
//            }
//            else if nodesArray.first?.name == "exitButton" {
//                // exit
//            }
        }
    }
}
