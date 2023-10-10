package filtroTextura;

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
            case 'f':
                if(cena.filtro == GL2.GL_LINEAR){
                    cena.filtro = GL2.GL_NEAREST;
                    cena.texto = " GL_NEAREST";
                }
                else{
                    cena.filtro = GL2.GL_LINEAR;
                    cena.texto = " GL_LINEAR";
                }
                break;
            case '+':
                if(cena.limite <= 20.0f){
                    cena.limite += 0.1f;
                }
                System.out.println("limite: " + cena.limite);
                break;
            case '-':
                if(cena.limite >= 0.0f){
                    cena.limite -= 0.1f;
                }
                System.out.println("limite: " + cena.limite);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
