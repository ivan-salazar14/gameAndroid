package com.iasch.juego1;


import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;


public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener {

	private Camera mCamera;
	private Rectangle rectangulo;
	private static final int  WIDTH =800;
	private static final int  HEIGHT=480;
	private BitmapTextureAtlas miAtlas;
	private ITextureRegion texturaChar;
	private Sprite charSprite;
	private ITiledTextureRegion texturaAnimada;
	private AnimatedSprite spriteAnimado;
	private Sound  miSonido;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		
		mCamera = new Camera(0, 0, WIDTH, HEIGHT);
		
		//declaramos las  opciones del motor
		EngineOptions engineOptions= new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);
		
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		// impedimos que la pantalla se apague por inactividad
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() 	{
		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//se crea atlas, se define tamaño y textura
		miAtlas = new BitmapTextureAtlas(getTextureManager(), 800, 800,TextureOptions.DEFAULT);
		//ubicamos img en el atlas
		texturaChar= BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(miAtlas, this, "char.png",0,0,4,5);
		texturaAnimada= BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(miAtlas, this, "animado.png",76,0,10,10);
		miAtlas.load();//se carga
		
		
		try{
			//creo que la explicacion está demas
	    	//lo que si indico es que tambien hay una clase musica
	    	//pero se usa para canciones más largas
	    	//y que se repiten constantemente	    	
	     
			miSonido =SoundFactory.createSoundFromAsset( getSoundManager(), this,"sonido/sonido.mp3");
			
			//quiere decir q no se repite constantemente
			miSonido.setLooping(false);
			
		}   catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    
		//////////////////////////////////////////////////////////----------------------/////////////////////////////////////////
		//rectangulo = new Rectangle(mCamera.getWidth()/2, mCamera.getHeight()/2, 100, 100, getVertexBufferObjectManager());
		// TODO Auto-generated method stub
		//rectangulo.setColor(0.3f, 0.5f, 0.2f);
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		Scene scene = new Scene();
		spriteAnimado= new AnimatedSprite(200,200, texturaAnimada, getVertexBufferObjectManager());
		
		//tiempo que se demora para cada frame
		long[] duracionFrame= {200,200,200,200,200,200};
		// t frame, del frame 1 al 6, siempre repitiendose
		spriteAnimado.animate(duracionFrame,1,6,true);
		
		charSprite=new Sprite(100, 200, texturaChar, getVertexBufferObjectManager());
		scene.attachChild(charSprite);
		scene.attachChild(spriteAnimado);
		
		
		//para que el callback del touch listener funcione hay q adicinarla a una escena
		scene.setOnSceneTouchListener(this);
	//	scene.attachChild(rectangulo);
		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		if(pSceneTouchEvent.isActionDown()){
			
			miSonido.play();
		}
		
		return false;
	}

}
