//
//  RegisterViewController.swift
//  finalProject
//
//  Created by Junyoung Kim on 2019-08-01.
//  Copyright © 2019 Junyoung Kim. All rights reserved.
//

import Foundation
import UIKit
import CoreData

class RegisterViewController: UIViewController {
    @IBOutlet weak var username: UITextField!
    @IBOutlet weak var password: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    @IBAction func registerButtonHandler(_ sender: Any) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        let context = appDelegate.persistentContainer.viewContext
        let userEntity = NSEntityDescription.entity(forEntityName: "Account", in: context)!
        
        let user = NSManagedObject(entity: userEntity, insertInto: context)
        user.setValue(username.text, forKey: "username")
        user.setValue(password.text, forKey: "password")
        user.setValue(0, forKey: "score")
        
        do {
            try context.save()
        } catch let error as NSError {
            print("Save does not work \(error)")
        }
        print("Successful for registeration")
        // create the alert
        let alert = UIAlertController(title: "Registration", message: "Successful creating account", preferredStyle: UIAlertController.Style.alert)
        
        // add an action (button)
        alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil))
        
        // show the alert
        self.present(alert, animated: true, completion: nil)
        
    }
}
