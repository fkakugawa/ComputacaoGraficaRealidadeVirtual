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
        System.out.println("Key pressed: " + e.getKeyCode()+" "+e.getKeyChar());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        if(e.getKeyChar() == 'r')
            cena.angulo += 10;
        if(e.getKeyChar() =='t')
            cena.angulo -=10;

        if(e.getKeyChar() == 'f')
            cena.translacao = cena.translacao+0.1f;
        if(e.getKeyChar() == 'g')
            cena.translacao = cena.translacao-0.1f;

    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
