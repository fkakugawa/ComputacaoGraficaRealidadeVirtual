package cordaluz;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
/**
 *
 * @author Kakugawa
 */
public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        
        System.out.println("Key pressed: " + e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        switch (e.getKeyChar()) {
            case 'r':
                cena.angulo += 45;
                break;
            case 't':
                cena.tonalizacao = cena.tonalizacao == GL2.GL_SMOOTH ? GL2.GL_FLAT : GL2.GL_SMOOTH;
                break;
            case 'a':
                // inicia animacao
                cena.incAngulo = 1.0f;
                break;
            case 'q':
                // para a animacao
                cena.incAngulo = 0.0f;
                break;
            case '1':
                // luz totalmente branca
                System.out.println("100% Luz branca!");
                cena.luzR = 1.0f;
                cena.luzG = 1.0f;
                cena.luzB = 1.0f;
                break;
            case '2':
                // luz 20% branca
                System.out.println("50% de Luz branca!");
                cena.luzR = 0.5f;
                cena.luzG = 0.5f;
                cena.luzB = 0.5f;
                break;
            case '3':
                // luz totalmente azul
                System.out.println("100% Luz azul!");
                cena.luzR = 0.0f;
                cena.luzG = 0.0f;
                cena.luzB = 1.0f;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
