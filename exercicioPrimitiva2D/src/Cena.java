import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
/**
 *
 * @author Kakugawa
 */
public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;    
    GLU glu;
    
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1;
        xMax = yMax = zMax = 1;        
    }

    @Override
    public void display(GLAutoDrawable drawable) { 
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glClearColor(0, 0, 0, 1);
        
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);       
        gl.glLoadIdentity();

         
        //desenha quatro pontos azuis
        gl.glColor3f(0,0,1);
        gl.glPointSize(3.0f);
        gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2f(-0.9f,0.9f);
            gl.glVertex2f(-0.9f,0.7f);
            gl.glVertex2f(-0.7f,0.9f);
            gl.glVertex2f(-0.7f,0.7f);
        gl.glEnd();
        gl.glFlush();
        
        //"peixe" vermelho
        gl.glColor3f(1,0,0);
        gl.glLineWidth(1);
        gl.glBegin(GL2.GL_LINE_LOOP);
            gl.glVertex2f(-0.5f,0.3f);
            gl.glVertex2f(-0.4f,0.5f);
            gl.glVertex2f(-0.3f,0.4f);
            gl.glVertex2f(-0.2f,0.5f);
            gl.glVertex2f(-0.2f,0.2f);
            gl.glVertex2f(-0.3f,0.3f);
            gl.glVertex2f(-0.4f,0.2f);
        gl.glEnd();
        gl.glFlush();  
        
        //reta transversal azul
        gl.glColor3f(0,0,1);
        gl.glLineWidth(10.0f);
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(0,0);
            gl.glVertex2f(-1,-1);
        gl.glEnd();
        gl.glFlush();
        
        //reta vermelha
        gl.glColor3f(1,0,0);
        gl.glLineWidth(5.0f);
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(0, 0);
            gl.glVertex2f(1, 0);
        gl.glEnd();
        gl.glFlush();
        
        //reta verde
        gl.glColor3f(0,1,0);
        gl.glLineWidth(3.0f);
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(0, 0);
            gl.glVertex2f(0, 1);
        gl.glEnd();
        gl.glFlush();
        
        //Poligono amarelo
        gl.glColor3f(1,1,0);
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2f(-0.3f, -0.5f);
            gl.glVertex2f(-0.9f,-0.4f);
            gl.glVertex2f(-0.7f,-0.1f);
            gl.glVertex2f(-0.5f,-0.2f);
            gl.glVertex2f(-0.2f,-0.1f);
        gl.glEnd();
        gl.glFlush();
        
        //triangulos conectados azuis
        gl.glColor3f(0,0,1);
        gl.glPointSize(3.0f);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
            gl.glVertex2f(0.2f, -0.9f);
            gl.glVertex2f(0.2f,-0.5f);
            gl.glVertex2f(0.5f,-0.4f);
            gl.glVertex2f(0.6f,-0.6f);
            gl.glVertex2f(0.8f,-0.7f);
            gl.glVertex2f(0.7f, -0.8f);
        gl.glEnd();
        gl.glFlush();
        
        //quadrado verde
        gl.glColor3f(0,1,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.6f, 0.3f);
            gl.glVertex2f(0.6f, 0.4f);
            gl.glVertex2f(0.7f, 0.4f);
            gl.glVertex2f(0.7f, 0.3f);
        gl.glEnd();
        gl.glFlush();
        
        //quadrado amarelo
        gl.glColor3f(1,1,0);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.7f, 0.2f);
            gl.glVertex2f(0.7f, 0.3f);
            gl.glVertex2f(0.8f, 0.3f);
            gl.glVertex2f(0.8f, 0.2f);
        gl.glEnd();
        gl.glFlush();
        
        //quadrado rosa
        gl.glColor3f(1,0,0.5f);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(0.8f, 0.1f);
            gl.glVertex2f(0.8f, 0.2f);
            gl.glVertex2f(0.9f, 0.2f);
            gl.glVertex2f(0.9f, 0.1f);
        gl.glEnd();
        gl.glFlush();
        
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();  
        
        //evita a divisão por zero
        if(height == 0) height = 1;
        //calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        //seta o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);
                
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //lê a matriz identidade
        
        //Projeção ortogonal
        //true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        //false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        if(width >= height)            
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
                
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}         
}
