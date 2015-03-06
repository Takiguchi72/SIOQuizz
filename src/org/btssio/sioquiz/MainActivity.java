package org.btssio.sioquiz;

import org.btssio.sioquiz.R.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	public Button depart;
	public EditText edtNom;
	private Intent intentJeu;
	private String nomJoueur;
	private Boolean[] reponses = new Boolean[3];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		depart = (Button)this.findViewById(R.id.btngo);
		depart.setOnClickListener(this);
		edtNom = (EditText)this.findViewById(R.id.edtNom);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		//Lorsqu'on clique sur le bouton, on créer une nouvelle instance pour lancer l'activité "JeuActivity"
		intentJeu = new Intent(this,JeuActivity.class);
		//Si la zone de saisie est vide,
		if(edtNom.getText().toString().equals(""))
		{
			//on affiche un msg d'erreur
			Toast.makeText(this, "Veuillez saisir votre nom !", Toast.LENGTH_LONG).show();
		}//fin if
		//si elle est remplie,
		else
		{
			//on stoque le contenu de la zone de texte dans une variable pour pouvoir l'utiliser dans une autre fonction
			nomJoueur = edtNom.getText().toString();
			//puis, on lance l'activité
			lancementJeu(0);
		}//fin else
	}//fin onClick
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//On récupère la réponse à la question posée, et on le stoque dans la variable reponses[numQuestion]
		reponses[data.getExtras().getInt("Numero")] = data.getExtras().getBoolean("Reponse");
		//Si le code retourné par l'activité "JeuActivity" est inférieur ou égal à 1, c'est qu'il reste des questions, donc on va continuer le quiz
		if (requestCode <= 1) {
			//On relance l'activité "JeuActivity" mais avec le numéro de question incrémenté pour passer à la question suivante
			lancementJeu(requestCode + 1);
		}
		//Si le code retourné par l'activité est supérieur à 1, c'est que le quiz est terminé
		else
		{
			Log.i("Étape", "Calcul du score final...");
			//on initialise le nombre de bonnes réponses à 0
			int nbBonnesReponses = 0;
			//pour chaque réponse,
			for(int i = 0 ; i < 3 ; i++)
			{
				//si le résultat est "true", on incrémente le nombre de bonnes réponses
				if ( reponses[i] == true)
					nbBonnesReponses++;
			}
			//On affiche le nombre de bonnes réponses
			Toast.makeText(this, "Vous avez eu " + nbBonnesReponses + " bonne(s) réponse(s) sur " + reponses.length + " !", Toast.LENGTH_LONG).show();
		}//fin else
	}//fin onActivityResult

	public void lancementJeu(int num) {
		//on spécifie une variable "Joueur" avec le contenu de la variable "nomJoueur"
		intentJeu.putExtra("Joueur",nomJoueur);
		//on spécifie une variable "Numero" avec le contenu de la variable "num"
		intentJeu.putExtra("Numero",num);
		//on lance l'activité, en passant les variables contenues dans "intentJeu"
		this.startActivityForResult(intentJeu, num);
	}//fin lancementJeu
}//fin class
