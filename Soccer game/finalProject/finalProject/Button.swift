//
//  Button.swift
//  finalProject
//
//  Created by Junyoung Kim on 2019-07-30.
//  Copyright © 2019 Junyoung Kim. All rights reserved.
//

import Foundation
import SpriteKit

protocol ButtonDelegate: class {
    func buttonClicked(button: Button)
}

class Button: SKSpriteNode {
    
    //weak so that you don't create a strong circular reference with the parent
    weak var delegate: ButtonDelegate!
    
    override init(texture: SKTexture?, color: SKColor, size: CGSize) {
        
        super.init(texture: texture, color: color, size: size)
        
        setup()
    }
    
    required init?(coder aDecoder: NSCoder) {  
        super.init(coder: aDecoder)
        
        setup()
    }
    
    func setup() {
        isUserInteractionEnabled = true
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        print("Touches began")
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
    
    }
}
