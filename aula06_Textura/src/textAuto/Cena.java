package textAuto;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT; //primitivas 3D
import textura.Textura;

/**
 *
 * @author Kakugawa
 */
public class Cena implements GLEventListener{

    private float angulo = 0;
    private GL2 gl;
    private GLU glu;
    private GLUT glut;
    private int tonalizacao = GL2.GL_SMOOTH;
    float luzR = 0.2f, luzG = 0.2f, luzB = 0.2f;
    public float incAngulo = 0;

    // Atributos    
    public float limite;
    float xMin, xMax, yMin, yMax, zMin, zMax;
    char tipo = 'c';

    //Referencia para classe Textura
    private Textura textura = null;
    //Quantidade de Texturas a ser carregada
    private int totalTextura = 1;

    //Constantes para identificar as imagens
//    public static final String FACE1 = "imagens/wood.jpg";
//    public static final String FACE1 = "imagens/cao.png";
    public static final String FACE1 = "imagens/bandeira.jpg";

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        GL2 gl = drawable.getGL().getGL2();

        angulo = 0;
        incAngulo = 0;
        limite = 1;

        //Cria uma instancia da Classe Textura indicando a quantidade de texturas
        textura = new Textura(totalTextura);

        //habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        gl = drawable.getGL().getGL2();
        glut = new GLUT(); //objeto da biblioteca glut

        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 0);
        //limpa a janela com a cor especificada
        //limpa o buffer de profundidade
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade

        /*
            desenho da cena        
        *
         */
        // criar a cena aqui....
        iluminacaoAmbiente();
        ligaLuz();

        gl.glPushMatrix();
            gl.glRotatef(angulo, 1, 1, 1);

            //transformações geométricas para as texturas
            gl.glMatrixMode(GL2.GL_TEXTURE);
            gl.glLoadIdentity();
            gl.glScalef(limite / textura.getWidth(), limite / textura.getHeight(), limite);
            gl.glMatrixMode(GL2.GL_MODELVIEW);

            //é geração de textura automática
            textura.setAutomatica(true);
    //        textura.setAutomatica(false);

            //habilita os filtros
            textura.setFiltro(GL2.GL_LINEAR);
            textura.setModo(GL2.GL_DECAL);
            textura.setWrap(GL2.GL_REPEAT);
            textura.gerarTextura(gl, FACE1, 0);

            if (tipo == 'c') {
                glut.glutSolidCube(50);
            }
            if (tipo == 'e') {
                glut.glutSolidSphere(50, 30, 30);
            }
            if (tipo == 't') {
                glut.glutSolidTorus(10, 50, 30, 30);
            }
            if (tipo == 'y') {
                glut.glutSolidCylinder(50, 80, 30, 30);
            }
            //desabilita a textura
            textura.desabilitarTexturaAutomatica(gl);

            desligaluz();

            //Rotacao do Cubo
            rotacionar();
        gl.glPopMatrix();

        gl.glFlush();
    }

    public void desenhaTexto(GL2 gl, int x, int y, String frase) {
        glut = new GLUT(); //objeto da biblioteca glut
        gl.glRasterPos2f(x, y);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_12, frase);
    }
    
    public void iluminacaoAmbiente() {
        float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f}; //cor
        float posicaoLuz[] = {0.0f, 0.0f, 100.0f, 1.0f}; //pontual

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

    // Animacao de rotacao do Cubo
    private void rotacionar() {
        angulo = angulo + incAngulo;
        if (angulo > 360f) {
            angulo = angulo - 360;
        }
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
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

}
