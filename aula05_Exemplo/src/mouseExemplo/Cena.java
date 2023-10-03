package mouseExemplo;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT; //primitivas 3D

/**
 *
 * @author Kakugawa
 */
public class Cena implements GLEventListener{

    public float angulo = 0;
    private GL2 gl;
    private GLU glu;
    private GLUT glut;
    public int tonalizacao = GL2.GL_SMOOTH;
    public boolean liga = true;

    public float mouseX = 0;
    public float mouseY = 0;
    public float tx = 0;
    public float ty = 0;
    public int larguraFrame;
    public int alturaFrame;

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        GL2 gl = drawable.getGL().getGL2();
        //habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        gl = drawable.getGL().getGL2();
        glut = new GLUT(); //objeto da biblioteca glut

        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        //limpa a janela com a cor especificada
        //limpa o buffer de profundidade
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade

        /*
            desenho da cena        
        *
         */
        // criar a cena aqui....
        if (liga) {
            iluminacaoAmbiente();
            ligaLuz();
        }

        gl.glRotatef(angulo, 0.0f, 1.0f, 1.0f);

        // criar a cena aqui....		
        gl.glColor3f(1, 0, 1);

        gl.glPointSize(5);
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2f(tx, ty);
        gl.glEnd();

        if (liga) {
            desligaluz();
        }

        gl.glFlush();
    }

    public void desenhaTexto(GL2 gl, int x, int y, String frase) {
        glut = new GLUT(); //objeto da biblioteca glut
        gl.glRasterPos2f(x, y);
        glut.glutBitmapString(GLUT.BITMAP_8_BY_13, frase);
    }

    public void iluminacaoAmbiente() {
        float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f}; //cor
        float posicaoLuz[] = {-50.0f, 0.0f, 100.0f, 1.0f}; //pontual

        // define parametros de luz de número 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    public void ligaLuz() {
        // habilita a definição da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da iluminação na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // habilita a luz de número 0
        gl.glEnable(GL2.GL_LIGHT0);
        //Especifica o Modelo de tonalizacao a ser utilizado 
        //GL_FLAT -> modelo de tonalizacao flat 
        //GL_SMOOTH -> modelo de tonalização GOURAUD (default)        
        gl.glShadeModel(tonalizacao);
    }

    public void desligaluz() {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT0);
        //desliga a iluminacao
        gl.glDisable(GL2.GL_LIGHTING);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        gl = drawable.getGL().getGL2();
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //lê a matriz identidade
        //projeção ortogonal (xMin, xMax, yMin, yMax, zMin, zMax)
        gl.glOrtho(-100, 100, -100, 100, -100, 100);
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        System.out.println("Reshape: " + width + ", " + height);
        
        larguraFrame = width;
        alturaFrame = height;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

//    @Override
//    public void keyPressed(KeyEvent e) {
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_ESCAPE:
//                System.exit(0);
//                break;
//            //........
//        }
//        switch (e.getKeyChar()) {
//            case 'r':
//                angulo += 45;
//                break;
//            case 't':
//                tonalizacao = tonalizacao == GL2.GL_SMOOTH ? GL2.GL_FLAT : GL2.GL_SMOOTH;
//                break;
//            // liga / desliga luz
//            case 'l':
//                if (liga) {
//                    liga = false;
//                } else {
//                    liga = true;
//                }
//                System.out.println(liga);
//                break;
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        int botao = e.getButton();
//
//        if(botao == MouseEvent.BUTTON1){
//            System.out.println("Clique ESQ");
//
//            //Pega as coordenados do clique do mouse
//            mouseX = (float)e.getX();
//            mouseY = (float)e.getY();	
//
//            //Realiza da conversão das coordenadas da tela para coordenadas da janela
//            tx = ( (2 * 100 * mouseX) / larguraFrame) - 100;
//            ty = ( ( (2 * 100) * (mouseY-alturaFrame) ) / - alturaFrame) - 100;
//            
//            System.out.println("tx: " + tx + ", ty: " + ty);
//        }
//        System.out.println("mouse");
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {}
//
//    @Override
//    public void mouseExited(MouseEvent e) {}
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        /*int botao = e.getButton();
//
//        if(botao == MouseEvent.BUTTON1){
//            System.out.println("Clique ESQ");
//
//            //Pega as coordenados do clique do mouse
//            mouseX = (float)e.getX();
//            mouseY = (float)e.getY();	
//
//            //Realiza da conversão das coordenadas da tela para coordenadas da janela
//            tx = ( (2 * 100 * mouseX) / larguraFrame) - 100;
//            ty = ( ( (2 * 100) * (mouseY-alturaFrame) ) / - alturaFrame) - 100;
//            
//            System.out.println("tx: " + tx + ", ty: " + ty);
//        }
//        System.out.println("mouse");
//        */
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {}
//
//    @Override
//    public void mouseMoved(MouseEvent e) {}
//
//    @Override
//    public void mouseDragged(MouseEvent e) {}
//
//    @Override
//    public void mouseWheelMoved(MouseEvent e) {}
}
