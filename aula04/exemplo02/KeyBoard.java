package aula04.exemplo02;
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
                System.out.println("chamou Rotacao");
                cena.ang += 45.0f;
                break;

            case '+':
                cena.size += 1;
                cena.radio += 2;
                cena.height += 2;
                cena.innerRadius += 1;
                break;

            case '-':
                cena.size -= 1;
                cena.radio -= 2;
                cena.height -=2;
                cena.innerRadius -= 1;
                break;

            case 'o':
                cena.outerRadius += 1;
                break;

            case 'O':
                cena.outerRadius -= 1;
                break;

            case 's':
                cena.slices += 1;
                break;

            case 'S':
                cena.slices -= 1;
                break;

            case 't':
                cena.stacks += 1;
                cena.rings += 1;
                break;

            case 'T':
                cena.stacks -= 1;
                cena.rings -= 1;
                break;

            case 'w':
                System.out.println("mode: " + cena.mode);

                if(cena.mode == GL2.GL_FILL){ 
                    cena.mode = GL2.GL_LINE; 
                }else{
                    cena.mode = GL2.GL_FILL;
                }                
                break;

             case 'x':
             case 'X':
                 cena.reset();
                 break;
                 
            //verifica qual primitiva sera desenhada
            case '1':
                cena.op = 1;
                break;
            case '2':
                cena.op = 2;
                break;
            case '3':
                cena.op = 3;
                break;
            case '4':
                cena.op = 4;
                break;
            case '5':
                cena.op = 5;
                break;
            case '6':
                cena.op = 6;
                break;
        }         
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
