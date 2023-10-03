package mouseExemplo;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GL2;
/**
 *
 * @author Kakugawa
 */
public class KeyBoard implements KeyListener, MouseListener{
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
            case 'l':
                cena.liga = !cena.liga;
                System.out.println(cena.liga);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) {   
        int botao = e.getButton();

        if(botao == MouseEvent.BUTTON1){
            System.out.println("Clique ESQ");

            //Pega as coordenados do clique do mouse
            cena.mouseX = (float)e.getX();
            cena.mouseY = (float)e.getY();	

            //Realiza da conversão das coordenadas da tela para coordenadas da janela
            cena.tx = ( (2 * 100 * cena.mouseX) / cena.larguraFrame) - 100;
            cena.ty = ( ( (2 * 100) * (cena.mouseY-cena.alturaFrame) ) / - cena.alturaFrame) - 100;
            
            System.out.println("tx: " + cena.tx + ", ty: " + cena.ty);
        }
        System.out.println("mouse");
    }

    @Override
    public void mouseEntered(MouseEvent e) {  }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseEvent e) {}

}
