package cordaluz;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT; //primitivas 3D
import java.awt.Color;
import java.awt.Font;
/**
 *
 * @author Kakugawa
 */
public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;        
    public float angulo=0, incAngulo=0;        
    public boolean zbuffer = true;
    private TextRenderer textRenderer;
    //Preenchimento
    public int mode;  
    public int tonalizacao = GL2.GL_SMOOTH;
    public float luzR = 0.2f, luzG = 0.2f, luzB = 0.2f;
        
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena        
        GL2 gl = drawable.getGL().getGL2();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;
        
        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.PLAIN, 15));
        //Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {  
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();                
        //objeto para desenho 3D
        GLUT glut = new GLUT();
        
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(1, 1, 1, 1);        
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);       
        gl.glLoadIdentity(); //ler a matriz identidade
        
        /*
            desenho da cena        
        *
        */
        
        iluminacao(gl);
        ligaLuz(gl);
        
        gl.glPushMatrix();
            gl.glColor3f(1, 0,0);
            gl.glRotatef(angulo, 0, 1, 1);
            desenhaTorus(glut);
            rotacionarTorus();
        gl.glPopMatrix();
        ///// Exemplo de Texto        
        gl.glColor3f(0, 0, 0);
        desenhaTexto(gl, 0, 570, Color.BLACK, "Angulo: " + angulo);
       
        gl.glFlush();      
    }
    
        
    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){         
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        //Retorna a largura e altura da janela
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);       
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }
    
    // Desenha um toroide na cena
    private void desenhaTorus(GLUT glut) {
        glut.glutSolidTorus(30, 60, 8, 25);
    }
    
    // Animacao de rotacao do Torus
    private void rotacionarTorus() {
        angulo = angulo + incAngulo;        
        if (angulo > 360f) {
            angulo = angulo - 360;
        }
    }

    public void iluminacao(GL2 gl) {                
        float luzAmbiente[] = new float[4];
        luzAmbiente[0] = luzR;
        luzAmbiente[1] = luzG;
        luzAmbiente[2] = luzB;
        luzAmbiente[3] = 0.5f;

        // define os parametros de luz de numero 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);

    }

    public void ligaLuz(GL2 gl) {
        // habilita a definicao da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        // habilita o uso da iluminacao na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // habilita a luz de numero 0
        gl.glEnable(GL2.GL_LIGHT0);

        /*
	* Especifica o Modelo de tonalizacao a ser utilizado GL_FLAT -> modelo de
	* tonalizacao flat GL_SMOOTH -> modelo de tonalizacao GOURAUD (default)
         */
        gl.glShadeModel(tonalizacao);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();  
        
        //evita a divisao por zero
        if(height == 0) height = 1;
        //calcula a proporcao da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
                
        //ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //ler a matriz identidade

        //projecao ortogonal sem a correcao do aspecto
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);
        
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //ler a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}
}
