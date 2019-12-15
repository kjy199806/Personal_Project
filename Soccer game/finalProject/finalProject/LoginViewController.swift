//
//  LoginVIewController.swift
//  finalProject
//
//  Created by Junyoung Kim on 2019-08-01.
//  Copyright © 2019 Junyoung Kim. All rights reserved.
//

import Foundation
import UIKit
import CoreData
import SpriteKit
import GameplayKit

class LoginViewController: UIViewController {

    @IBOutlet weak var username: UITextField!
    @IBOutlet weak var password: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var playButton: UIButton!
    @IBOutlet weak var registerButton: UIButton!
    

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (segue.identifier! == "segue"){
            var MainMenu = segue.destination as! GameViewController
            MainMenu.username = username.text!
        }

    }

    
    @IBAction func loginButtonHandler(_ sender: Any) {
        
        if (username.text! != "" && password.text! != ""){
            let appDelegate = UIApplication.shared.delegate as! AppDelegate
            
            let context = appDelegate.persistentContainer.viewContext
            
            let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Account")
            
            
            
            
            request.predicate = NSPredicate(format: "username = %@", username.text!)
            
            request.returnsObjectsAsFaults = false
            do {
                let result = try context.fetch(request)
                for data in result as! [NSManagedObject] {
                    
                    let passwordFromData = data.value(forKey: "password") as! String
                    if password.text! == passwordFromData {
                        // Log in successful
                        //                    successfulLabel.isHidden = false
                        //                    playButton.isHidden = false
                        username.isEnabled = false
                        password.isEnabled = false
                        loginButton.isEnabled = false
                        registerButton.isEnabled = false
                        print("Successful")
                        performSegue(withIdentifier: "segue", sender: self)
                        
                    }
                    else{
                        print("Failed login")
                    }
                }
            } catch {
                print("Failed")
            }
        }
        
    }
    
}
