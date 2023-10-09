import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.*;
/**
 *
 * @author Kakugawa
 */
public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;        
    public float angulo=0;        
    public int tonalizacao = GL2.GL_SMOOTH;            
    public boolean liga = true;          
    public int ponto = 0, modo = GL2.GL_FILL;
    private TextRenderer textRenderer;

    public float especularMove;
    public float difusaMove;
        
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena        
        GL2 gl = drawable.getGL().getGL2();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -200;
        xMax = yMax = zMax = 200;  
        
        textRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 16));
        //Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);

        especularMove = difusaMove = 70;
    }

    @Override
    public void display(GLAutoDrawable drawable) {  
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();                
        //objeto para desenho 3D
        GLUT glut = new GLUT();
        
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);        
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);       
        gl.glLoadIdentity(); //ler a matriz identidade
        
        /*
            desenho da cena        
        *
        */        
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, modo);        
        if (liga){
            ligaIluminacao(gl);
            desenhaTexto(gl, 10, 560, Color.green, "Luz ligada");
        }
        else{
            desligaIluminacacao(gl);
            desenhaTexto(gl, 10, 560, Color.red, "Luz desligada");
        }

        ligaPontoLuz(ponto, gl);

        gl.glPushMatrix();
            gl.glRotatef(angulo, 0.0f, 1.0f, 1.0f);
            desenhaEsfera(gl, glut);
        gl.glPopMatrix();

        desligaPontoLuz(ponto, gl);

        if (liga)
            desligaIluminacacao(gl);
        
        gl.glFlush();
    }

    private void esfera(GL2 gl, GLUT glut) {
        gl.glPushMatrix();
            glut.glutSolidSphere(50, 20, 20);
        gl.glPopMatrix();
    }
    
    private void desenhaEsfera(GL2 gl, GLUT glut){
        gl.glColor3f(1, 0, 0); //vemelho
        gl.glPushMatrix();
            gl.glTranslated(-15, 0, 0);
            esfera(gl, glut);
        gl.glPopMatrix();
        
        gl.glColor3f(0, 0, 1); //azul
        gl.glPushMatrix();
            gl.glTranslated(15, 0, 0);
            esfera(gl, glut);
        gl.glPopMatrix();
    }

    public void iluminacaoEspecular(GL2 gl) {
        float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f}; //cor
        float luzEspecular[] = {1.0f, 1.0f, 1.0f, 1.0f}; //cor
        float posicaoLuz[] = {-70.0f, especularMove, 100.0f, 1.0f}; //pontual

        gl.glEnable(GL2.GL_LIGHT0);
        
        //intensidade da reflexao do material        
        int especMaterial = 128;
        //define a concentracao do brilho
        gl.glMateriali(GL2.GL_FRONT, GL2.GL_SHININESS, especMaterial);

        //define a reflect�ncia do material
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, luzEspecular, 0);

        //define os par�metros de luz de n�mero 2 (dois)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, luzEspecular, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }
    
    
    public void iluminacaoDifusa(GL2 gl) {
        float luzDifusa[] = {0.5f, 0.5f, 0.5f, 1.0f}; //cor
        float posicaoLuz[] = {difusaMove, -90.0f, 100.0f, 1.0f}; //1.0 pontual

        gl.glEnable(GL2.GL_LIGHT1);
        //define os par�metros de luz de n�mero 1 (um)
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, luzDifusa, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, posicaoLuz, 0);
    }
    
    public void ligaIluminacao(GL2 gl){
        // habilita a defini��o da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        // habilita o uso da ilumina��o na cena
        gl.glEnable(GL2.GL_LIGHTING);
        
        //Especifica o Modelo de tonalizacao a ser utilizado 
        //GL_FLAT -> modelo de tonalizacao flat 
        //GL_SMOOTH -> modelo de tonaliza��o GOURAUD (default)        
        gl.glShadeModel(tonalizacao);
    }
        
    public void ligaPontoLuz(int numLuz, GL2 gl) {    
        // habilita a luz de n�mero
        switch(numLuz){
            case 0:
                iluminacaoEspecular(gl);
                desenhaTexto(gl, 10, 580, Color.white, "ESPECULAR");
                break;
            case 1:
                iluminacaoDifusa(gl);
                desenhaTexto(gl, 10, 580, Color.gray, "DIFUSA");
                break;
        }        
    }

    public void desligaPontoLuz(int numLuz, GL2 gl){
        switch(numLuz){           
            case 0:
                gl.glDisable(GL2.GL_LIGHT0);                
                break;                
            case 1:
                gl.glDisable(GL2.GL_LIGHT1);                
                break;                
        }
    }
    
    public void desligaIluminacacao(GL2 gl) {        
        //desliga a iluminacao
        gl.glDisable(GL2.GL_LIGHTING);
    }
    
    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){         
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        //Retorna a largura e altura da janela
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, modo);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();  
        
        //evita a divisao por zero
        if(height == 0) height = 1;
        //calcula a proporcao da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        //seta o viewport para abranger a janela inteira
        //gl.glViewport(0, 0, width, height);
                
        //ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //ler a matriz identidade

        //projecao ortogonal sem a correcao do aspecto
        if(width >= height)
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
        
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //ler a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}         
}
