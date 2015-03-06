package org.btssio.sioquiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class JeuActivity extends Activity implements OnClickListener {
	private TextView entete;
	private TextView laQuestion;
	private RadioGroup groupQuestions;
	private RadioButton[] reponses;
	private Button resultat;
	private int numero;
	private String questions[][] = {
			{ "John McLane", "Piège en eaux troubles", "Pulp Fiction", "Piège de Cristal", "3" },
			{ "Martin McFly", "Mars Attacks !", "Spin City", "Retour vers le futur", "3" }, 
			{ "Alan Parrish", "Jumanji", "Good Morning, Vietnam", "Popeye", "1" }
	};
	private Boolean reponse;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jeu);
		//Récupération du nom du joueur grâce à la clef "Joueur" passée par MainActivity lors du lancement de cette activité
		String nomJoueur = this.getIntent().getExtras().getString("Joueur");
		//Récupération du numéro de question
		numero = this.getIntent().getExtras().getInt("Numero");
		//Création de l'entête du quiz avec "À vous de jouer NOM !
		entete = (TextView)this.findViewById(R.id.txvNom);
		entete.setText("À vous de jouer " + nomJoueur + " !");
		
		// Initialisation des contrôles
		laQuestion = (TextView)findViewById(R.id.txvQuestion);
		 laQuestion.setMinimumWidth(400);
		 groupQuestions = (RadioGroup)findViewById(R.id.rdgQuestion);
		 reponses = new RadioButton[3];
		 // Création des boutons radio
		 reponses[0] = new RadioButton(this);
		 reponses[0].setTextColor(Color.parseColor("#1111FF"));
		 reponses[0].setMinimumWidth(400);
		 reponses[1] = new RadioButton(this);
		 reponses[1].setTextColor(Color.parseColor("#1111FF"));
		 reponses[1].setMinimumWidth(400);
		 reponses[2] = new RadioButton(this);
		 reponses[2].setTextColor(Color.parseColor("#1111FF")); 
		 reponses[2].setMinimumWidth(400);
		 // Affectation d'un ID aux boutons radio afin de savoir lequel est sélectionné
		 reponses[0].setId(0);
		 reponses[1].setId(1);
		 reponses[2].setId(2);
		 // Gestion du bouton
		 resultat = (Button)findViewById(R.id.btnReponse);
		 resultat.setOnClickListener(this);
		 reponse = false;
		 // Démarrage du jeu
		initQuestions(numero);
	}//fin onCreate
	
	private void initQuestions(int numero) {
		//affichage de la question
		 laQuestion.setText("Dans quel film le personnage de "+questions[numero][0]+ " joue ?");
		 //affichage du premier bouton radio avec l'association de la réponse au bouton
		 reponses[0].setText(questions[numero][1]);
		 groupQuestions.addView(reponses[0]);
		 //affichage du deuxième bouton radio avec l'association de la réponse au bouton
		 reponses[1].setText(questions[numero][2]);
		 groupQuestions.addView(reponses[1]);
		 //affichage du troisième bouton radio avec l'association de la réponse au bouton
		 reponses[2].setText(questions[numero][3]);
		 groupQuestions.addView(reponses[2]);
	}//fin initQuestions

	@Override
	public void onClick(View arg0) {
		//on récupère le bouton radio qui est coché
		int retour = groupQuestions.getCheckedRadioButtonId();
		//on récupère la bonne réponse à la question
		int repOk = Integer.parseInt(questions[numero][4]);
		//si le bouton radio coché correspond à la bonne réponse
		if (retour+1 == repOk ){
			//on affiche "Bonne réponse"
			Toast.makeText(this,"Bonne réponse !", Toast.LENGTH_LONG).show();
			//et on affecte "true" à la variable "reponse"
			reponse = true;
		}//fin if
		//si c'était le mauvais bouton radio de coché, on affiche "Mauvaise réponse"
		else
		{
			Toast.makeText(this,"Mauvaise réponse, c'était : "+questions[numero][repOk],Toast.LENGTH_LONG).show();
		}//fin else
		
		//On prépare le lancement de la deuxième question
		Intent returnIntent = new Intent();
		//on renseigne les valeurs que l'on veut retourner
		returnIntent.putExtra("Numero",numero);
		returnIntent.putExtra("Reponse",reponse);
		//on "lie" les valeurs à retourner au "return"
		setResult(RESULT_OK,returnIntent);
		finish();//fin de l'activité
	}//fin onClick	
}//fin classe