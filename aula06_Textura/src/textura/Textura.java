package textura;

import com.jogamp.opengl.GL2;
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 *
 * @author Kakugawa
 */
public class Textura {
    private Texture vetTextures[]; //novo   
    private float width;
    private float height;
    private int filtro;
    private int modo;
    private int wrap;
    private boolean automatica;

    //Construtor da Classe Textura
    public Textura(int totalTextura) {
        vetTextures = new Texture[totalTextura];
    }

    //getters/setters
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public float getFiltro() {
        return filtro;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public float getModo() {
        return modo;
    }

    public void setWrap(int wrap) {
        this.wrap = wrap;
    }

    public float getWrap() {
        return wrap;
    }
    
    public void setAutomatica(boolean automatica){
        this.automatica = automatica;
    }
    public boolean getAutomatica(){
        return automatica;
    }

    /**
     * Método para gerar a textura - filtros e resulocao
     *
     * @param gl GL2 - Contexto opengl
     * @param fileName String - Localizacao do arquivo de imagem
     * @param indice int - Indice da imagem
     */
    public void gerarTextura(GL2 gl, String fileName, int indice) {
        carregarTextura(fileName, indice);
        Texture tex = vetTextures[indice];
        tex.bind(gl); //vincula a textura
        tex.enable(gl); //habilita a textura

        // Define os filtros de aumento e reducao
        //GL_NEAREST ou GL_LINEAR                
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, filtro);
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, filtro);

        //GL.GL_REPEAT ou GL.GL_CLAMP        
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, wrap);
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, wrap);

        //GL.GL_MODULATE ou GL.GL_DECAL ou GL.GL_BLEND
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, modo);
        if(this.automatica)
            habilitarTexturaAutomatica(gl);
    }

    /**
     * Metodo para carregar o arquivo da textura
     *
     * @param fileName String - Localizacao do arquivo de imagem
     * @param indice int - indice da textura
     */
    private void carregarTextura(String fileName, int indice) {
        Texture tex = null;

        //carrega o arquivo da imagem
        try {
            tex = TextureIO.newTexture(new File(fileName), true);
        } catch (IOException e) {
            System.out.println("\n=============\nErro na leitura do arquivo "
                    + fileName + "\n=============\n");
        }

        //Obtem a largura e altura da imagem
        this.width = tex.getWidth();
        this.height = tex.getHeight();

        //armazena a textura no vetor
        vetTextures[indice] = tex;
    }//fim loadImage

     /**
     * Metodo para habilitar o uso da textura automatica
     *
     * @param gl GL2 - Contexto opengl
     //* @param genModo - Modo de geracao da textura
     */
    public void habilitarTexturaAutomatica(GL2 gl) {
        int genModo = GL2.GL_OBJECT_LINEAR; //GL.GL_EYE_LINEAR ou GL_OBJECT_LINEAR ou GL_SPHERE_MAP
//        int genModo = GL2.GL_EYE_LINEAR;

        float planoS[] = {1.0f, 0.0f, 0.0f, 0.0f};
        float planoT[] = {0.0f, 0.0f, 1.0f, 0.0f};

        gl.glEnable(GL2.GL_TEXTURE_GEN_S); // Habilita a geracao da textura
        gl.glEnable(GL2.GL_TEXTURE_GEN_T);
//        gl.glEnable(GL2.GL_TEXTURE_2D);
       
        gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, genModo);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, genModo);

        //GL_EYE_PLANE ou GL_OBJECT_PLANE
        gl.glTexGenfv(GL2.GL_S, GL2.GL_OBJECT_PLANE, planoS, 0);
        gl.glTexGenfv(GL2.GL_T, GL2.GL_OBJECT_PLANE, planoT, 0);
    }

    /**
     * Metodo para desabilitar o uso de textura automatica
     *
     * @param gl GL2 - Contexto opengl
     */
    public void desabilitarTexturaAutomatica(GL2 gl) {
        //desabilita o uso de textura
        gl.glDisable(GL2.GL_TEXTURE_GEN_S);
        gl.glDisable(GL2.GL_TEXTURE_GEN_T);
//        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    /**
     * Metodo para desabilitar o uso de textura
     *
     * @param gl GL2 - Contexto opengl
     * @param indice int - indice da textura
     */
    public void desabilitarTextura(GL2 gl, int indice) {        
        Texture tex = vetTextures[indice];
        tex.disable(gl); //desabilita a textura
        tex.destroy(gl); //destroi os recursos
        if(this.automatica)
            desabilitarTexturaAutomatica(gl);
    }
}