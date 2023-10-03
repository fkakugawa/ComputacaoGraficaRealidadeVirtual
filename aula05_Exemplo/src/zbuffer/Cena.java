package zbuffer;

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
    public float angulo;        
    public boolean zbuffer = true;
    private TextRenderer textRenderer;
    //Preenchimento
    public int mode;  
    
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
        
        if (zbuffer) {
            gl.glEnable(GL2.GL_DEPTH_TEST);
        } else {
            gl.glDisable(GL2.GL_DEPTH_TEST);
        }
        
        iluminacao(gl);
        ligaLuz(gl);
        
        gl.glPushMatrix();
            //rotaciona o cubo na cena
            gl.glRotated(angulo, 0, 1, 1);

            gl.glRotatef(20, -1.0f, -1.0f, -1.0f);
            // CUBO LARANJA
            gl.glPushMatrix();
                gl.glColor3f(1.0f, 0.5f, 0.0f);
                gl.glTranslatef(-15.0f, 0.0f, 0.0f);
                cubo(glut);
            gl.glPopMatrix();

            // CUBO AZUL
            gl.glPushMatrix();
                gl.glColor3f(0.0f, 0.5f, 1.0f);
                gl.glRotatef(50.0f, 1.0f, 0.0f, -1.0f);
                gl.glTranslatef(30.0f, 0.0f, 0.0f);
                cubo(glut);
            gl.glPopMatrix();
        gl.glPopMatrix();
        
        ///// Exemplo de Texto        
        gl.glColor3f(0, 0, 0);
        desenhaTexto(gl, 10, 570, Color.BLACK, "Z-BUFFER: " + zbuffer);
               
        gl.glFlush();      
    }
    // Desenha um cubo na cena
    private void cubo(GLUT glut) {
        glut.glutSolidCube(80);
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

    public void iluminacao(GL2 gl) {
        float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f};
        float luzDifusa[] = {0.7f, 0.7f, 0.7f, 1.0f};
        float luzEspecular[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float posicaoLuz[] = {0.0f, 50.0f, 50.0f, 0.0f};

        // capacidade de brilho do material
        float especularidade[] = {1.0f, 1.0f, 1.0f, 1.0f};
        int especMaterial = 60;

        // define a reflectancia do material
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, especularidade, 0);

        // define a concentracao do brilho
        gl.glMateriali(GL2.GL_FRONT, GL2.GL_SHININESS, especMaterial);

        // ativa o uso da luz ambiente
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, luzAmbiente, 0);

        // define os parametros de luz de numero 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, luzDifusa, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, luzEspecular, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }
    
    public void ligaLuz(GL2 gl) {
        // habilita a definicao da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        // habilita o uso da iluminacao na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // habilita a luz de numero 0
        gl.glEnable(GL2.GL_LIGHT0);

        /*
            Especifica o Modelo de tonalizacao a ser utilizado 
            GL_FLAT -> modelo de tonalizacao flat 
            GL_SMOOTH -> modelo de tonalizacao GOURAUD (default)
         */
        gl.glShadeModel(GL2.GL_SMOOTH);
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
