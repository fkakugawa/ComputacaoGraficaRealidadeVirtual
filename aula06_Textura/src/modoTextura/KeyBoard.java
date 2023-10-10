package modoTextura;

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
            case 't': //para a animacao
                cena.triangulo = !cena.triangulo;
                break;
            case '4':
                cena.modo = GL2.GL_MODULATE;
                cena.texto = " GL_MODULATE";
                break;
            case '5':
                cena.modo = GL2.GL_DECAL;
                cena.texto = " GL_DECAL";
                break;
            case '6':
                cena.modo = GL2.GL_BLEND;
                cena.texto = " GL_BLEND";
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
