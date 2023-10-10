package textAuto;

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
            case KeyEvent.VK_ESCAPE:
                /*  Escape Key */
                System.exit(0);
                break;

            case '1': //inicia animacao
                cena.incAngulo = 2.0f;
                break;

            case '2': //para a animacao
                cena.incAngulo = 0f;
                break;
            case '+':
                if (cena.limite <= 200.0f) {
                    cena.limite += 0.1f;
                }
                System.out.println("limite: " + cena.limite);
                break;
            case '-':
                if (cena.limite >= 0.0f) {
                    cena.limite -= 0.1f;
                }
                System.out.println("limite: " + cena.limite);
                break;
            case 'c':
                cena.tipo = 'c';
                break;
            case 'e':
                cena.tipo = 'e';
                break;
            case 't':
                cena.tipo = 't';
                break;
            case 'y':
                cena.tipo = 'y';
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
