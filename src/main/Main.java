package me.abbatrombone.traz;


import java.awt.*;
import java.util.logging.Logger;

public class Main {
    //look into properties files
    public static void main(String[] args){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Init.class.getName()).log(java.util.logging.Level.SEVERE, "ClassNotFoundException", ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Init.class.getName()).log(java.util.logging.Level.SEVERE, "InstantiationException", ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Init.class.getName()).log(java.util.logging.Level.SEVERE, "IllegalAccessException", ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Init.class.getName()).log(java.util.logging.Level.SEVERE, "UnsupportedLookAndFeelException", ex);
        }
        EventQueue.invokeLater(() -> new Init().setVisible(true));
    }
}