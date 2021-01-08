package com.example.birdie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

import javax.xml.soap.Text;

public class  Birdie extends ApplicationAdapter {
//oyunlarda sprite denen objelerle çalısılır
	//batchler spriteleri çizmemız için yardımcı olan metotlar-objeler

	SpriteBatch batch;
	Texture background;
	Texture bird;

	float birdX = 0;
	float birdY = 0;

	int gameState = 0;
	float speed = 0;
	float gravity = 0.2f;
	Texture enemy2, enemy3, enemy4 ;
	float enemySpeed = 4;
	float distance  = 0;
	Random random;
	int numberOfEnemies = 4;
	Circle birdCircle;




	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOfSet = new float[numberOfEnemies];
	float [] enemyOfSet2 = new float[numberOfEnemies];
	float [] enemyOfSet3 = new float[numberOfEnemies];

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;
	@Override
	public void create () { //oyun basladıgında ne olacak

		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy2 = new Texture("enemy2.png");
		enemy3 = new Texture("enemy3.png");
		enemy4 = new Texture("enemy4.png");
		random = new Random();

		font = new BitmapFont();
		font.setColor(Color.PINK);
		font.getData().setScale(6);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(8);

		distance = Gdx.graphics.getWidth() / 2;

		//EKRANDA HAREKET EDECEK OLAN KUŞUN X VE Y EKSENLERININ DEĞERLERİ
		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() ;
		birdY = Gdx.graphics.getHeight() / 3 ;


		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		for(int i = 0 ; i< numberOfEnemies ; i++){
			enemyOfSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOfSet2[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight()-200);
			enemyOfSet3[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight()-200);
			enemyX[i] = Gdx.graphics.getWidth() - enemy2.getWidth() / 2 + i*distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}

	}



	@Override//oyun devam ettiği sürece sureklı çalısan method
	public void render () {
		batch.begin();
		//burada width v height değerlerini hangi cihaz kullanılıyorsa ona gore ayarlaması için gdx.graphics ile yaptık
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if(gameState == 1){

			if(enemyX[scoredEnemy] < birdX) {
				score++; //kuş her ilerlediğinde score 1 artar
				if(score>=3 && score<6){//Score arttıkça oyun zorlaşsın istediğim için enemyspeedi arttırıyorum
					enemySpeed++;
				}
				if(score>=6 && score<10) {
					enemySpeed = enemySpeed+2;
				}
				if(score>=10 && score<15) {
					enemySpeed = enemySpeed+3;
				}
				if(score>=15 && score<20) {
					enemySpeed = enemySpeed+4;
				}

				if(scoredEnemy < 3) {
					scoredEnemy++;
				}else {
					scoredEnemy = 0;
				}
			}



			if(Gdx.input.justTouched()) {
				speed = - 8;
			}
			for(int i = 0 ; i < numberOfEnemies ; i++){
				if(enemyX[i] < -enemy2.getWidth() || enemyX[i] < -enemy3.getWidth() || enemyX[i] < -enemy4.getWidth()) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

						enemyOfSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOfSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
						enemyOfSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				}else{
					enemyX[i] = enemyX[i] - enemySpeed;
				}




				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 24, Gdx.graphics.getHeight() / 2 + enemyOfSet[i] + Gdx.graphics.getHeight() /12, Gdx.graphics.getWidth() / 24 );
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 28, Gdx.graphics.getHeight() / 2 + enemyOfSet2[i] + Gdx.graphics.getHeight() /16, Gdx.graphics.getWidth() / 24 );
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 24, Gdx.graphics.getHeight() / 2 + enemyOfSet3[i] + Gdx.graphics.getHeight() /10, Gdx.graphics.getWidth() / 24 );
				if(enemyCircles[i] != enemyCircles2[i] && enemyCircles[i] != enemyCircles3[i] && enemyCircles2[i] != enemyCircles3[i] ) {
					batch.draw(enemy3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfSet[i], Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 6);
					batch.draw(enemy4, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfSet2[i], Gdx.graphics.getWidth() / 14, Gdx.graphics.getHeight() / 8);
					batch.draw(enemy2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfSet3[i], Gdx.graphics.getWidth() / 12, Gdx.graphics.getHeight() / 5);
				}

			}



			if(birdY > 0 && birdY < Gdx.graphics.getHeight()-120 ){
				speed = speed + gravity; ;
				birdY = birdY - speed ;
			}

			else {//kuş ekranın en altındayada en ustunde konumlanırsa oyunun bitmesi için
				gameState = 2;
			}

		}
		else if (gameState == 0){
			if(Gdx.input.justTouched()) { //ekrana dokunuldugunda oyun başlar
				gameState = 1 ;
			}
		}

		else if (gameState == 2) { //çarpısma gerçekleştiyse oyun sonlanır

			font2.draw(batch,String.valueOf(" GAME OVER "),Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2);

			if(Gdx.input.justTouched()) { //oyun sonlandığında sonra tekrar oyunu baslatmak için..
				gameState = 1 ;
				birdY = Gdx.graphics.getHeight() / 3 ; //tekrar başlamk için kuşun y eksenını sıfırlayıp ilk haline getirdim

				for(int i = 0 ; i< numberOfEnemies ; i++){

					enemyOfSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOfSet2[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight()-200);
					enemyOfSet3[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight()-200);


					enemyX[i] = Gdx.graphics.getWidth() - enemy2.getWidth() / 2 + i*distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				//oyun bittiğinde değerleri default haline getiriyouz
				score = 0;
				scoredEnemy = 0;
				speed = 0;
				enemySpeed = 3;
			}
		}


		batch.draw(bird, birdX , birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 9);
		font.draw(batch,String.valueOf("Score "+ score),70,120);

		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 18,Gdx.graphics.getWidth() / 30 );





		for (int i = 0; i<numberOfEnemies;i++) { //çarpışmaları burada kontrol edıyoruz

			if(Intersector.overlaps(birdCircle,enemyCircles[i] ) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])) {
				gameState = 2; //çarpışma gerçekleştiğinde oyun sonlanır

			}
		}
	}


	@Override
	public void dispose () {

	}
}
