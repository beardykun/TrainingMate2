//
//  ComposeView.swift
//  iosApp
//
//  Created by Mikhail Pankratov on 2023/08/28.
//  Copyright © 2023 orgName. All rights reserved.
//
import SwiftUI
import shared
import Foundation

struct ComposeView: UIViewControllerRepresentable {
    func updateUIViewController(_uiViewController: UIViewController, context: Context) {
    }
    
    func makeUIViewController(context: Context) -> some UIViewController {
        MainViewControllerKt.MainViewController()
    }
}
