package textObjetosQuadricos;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

/**
 *
 * @author Kakugawa
 */
public class Renderer {
    private static GLWindow window = null;
    public static int screenWidth = 400;
    public static int screenHeight = 400;
            
    //Cria a janela de rendeziração do JOGL
    public static void init(){        
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);        
        window = GLWindow.create(caps);
        window.setSize(screenWidth, screenHeight);
        //window.setResizable(false);
        
        Cena cena = new Cena();
        KeyBoard keyBoard = new KeyBoard(cena);

        window.addGLEventListener(cena); //adiciona a Cena a Janela  
        window.addKeyListener(keyBoard); //registra o teclado na janela
        
        //window.requestFocus();
        FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start(); //inicia o loop de animação
        
        //encerrar a aplicacao adequadamente
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });        
        //window.setFullscreen(true);
        window.setVisible(true);
    }
  
    public static void main(String[] args) {
        init();
    }
}
