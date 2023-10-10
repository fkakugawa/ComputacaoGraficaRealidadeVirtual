package textObjetosQuadricos;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            //........
        }
        switch (e.getKeyChar()) {
            case KeyEvent.VK_ESCAPE:  /*  Escape Key */
                System.exit(0);
                break;

            case '1': //inicia animacao
                cena.incAngulo = 1.0f;
                break;

            case '2': //para a animacao
                cena.incAngulo = 0f;
                break;

            case 'e':
                cena.tipo = 'e';
                break;
            case 'p':
                cena.tipo = 'p';
                break;
            case 'd':
                cena.tipo = 'd';
                break;
            case 'c':
                cena.tipo = 'c';
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
