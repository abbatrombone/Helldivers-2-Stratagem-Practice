package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Init.class.getName()).log(java.util.logging.Level.SEVERE, "ClassNotFoundException", ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Init.class.getName()).log(java.util.logging.Level.SEVERE, "InstantiationException", ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Init.class.getName()).log(java.util.logging.Level.SEVERE, "IllegalAccessException", ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Init.class.getName()).log(java.util.logging.Level.SEVERE, "UnsupportedLookAndFeelException", ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Init().setVisible(true));
    }
}
