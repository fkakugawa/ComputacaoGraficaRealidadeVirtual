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

        switch (e.getKeyCode()) {
            //Rotacao
            case KeyEvent.VK_R:
                cena.angulo += 45;
                break;
            //Tonalização
            case KeyEvent.VK_T:
                cena.tonalizacao = cena.tonalizacao == GL2.GL_SMOOTH ? GL2.GL_FLAT : GL2.GL_SMOOTH;
                break;
           //Ilumicacao
            case KeyEvent.VK_L:
                if(cena.liga)
                    cena.liga = false;
                else
                    cena.liga = true;
                System.out.println(cena.liga);
                break;
            //Preenchimento
            case KeyEvent.VK_W:
                cena.modo = cena.modo == GL2.GL_FILL ? GL2.GL_LINE : GL2.GL_FILL;
                break;
            //Iluminacao ESPECULAR
            case KeyEvent.VK_2:
                cena.ponto = 0;
                break;
            //Iluminacao DIFUSA
            case KeyEvent.VK_1:
                cena.ponto = 1;
                break;
            //Ponto de luz especular sobe
            case KeyEvent.VK_UP:
                if(cena.especularMove <= 100)
                    cena.especularMove += 10;
                System.out.println("ESPECULAR: " + cena.especularMove);
                break;
            //Ponto de luz especular desce
            case KeyEvent.VK_DOWN:
                if(cena.especularMove >= -100)
                    cena.especularMove -= 10;
                System.out.println("ESPECULAR: " + cena.especularMove);
                break;
            //Ponto de luz difusa vai para esquerda
            case KeyEvent.VK_RIGHT:
                if(cena.difusaMove <= 100)
                    cena.difusaMove +=10;
                System.out.println("DIFUSA: " + cena.difusaMove);
                break;
            //Ponto de luz difusa vai para direita
            case KeyEvent.VK_LEFT:
                if(cena.difusaMove >= -100)
                    cena.difusaMove -=10;
                System.out.println("DIFUSA: " + cena.difusaMove);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
