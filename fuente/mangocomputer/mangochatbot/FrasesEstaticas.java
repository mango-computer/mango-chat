/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangocomputer.mangochatbot;

import java.util.Random;

/**
 *
 * @author mm
 */
public class FrasesEstaticas 
{
    
    public static final String finalRecorrido[] = {
        "Disculpame, pero no logro entender lo que me dices, mi base de conocimiento en la actualidad es muy pequeña... &#9785",
        "He tratado de interpretar tu palabras, pero me no he podido hacerlo, mi base de conocimiento aun es muy limitada &#9785",
        "Se me ha hecho imposible enteder lo que me dices, espero me disculpes...&#9785",
        "Por mucho que lo intente no he podido saber lo que me dices, mi redes neuronales aun estan en crecimiento, ofrezco disculpas... &#9785",
        "Que verguenza, no logro decodificar tu mensaje, aun mi base de conocimiento es muy limitada, lo siento... &#9785",
        "Yo para lograr entender lo que me escribes me sustento en una base de conocimiento, por los momentos es muy pequeña y limitada, por esa razon no logre entender lo que quieres decirme... &#9785",
        "Cada vez que pasa el tiempo mi base de conocimiento crece, por los momentos es muy pequeña y por esa razon no puedo entender lo que me dices... &#9785",
        "Soy una computadora tratando de hacedr cosas humanas, yo se que las puedo hacer, pero para poder hacerlo necesito ampliar mi base de conocimiento, por los momentos es muy pequeña, lo siento, no puedo entender lo que me dices... &#9785",
        "No comprendo lo que me quieres decir, lo siento... &#9785",
        "Es para mi facil las operaciones matematicas, pero entener el lenguaje humano es otra cosa, cada dia aprendo, pero por los momentos mi base de conocimiento es muy limitada, lo siento, no entiendo tus palabras... &#9785"
    }; 
    
    public static int getNumeroAleatorio(int maximoValor)
    {
        Random aleatorio = new Random(System.currentTimeMillis());
        return aleatorio.nextInt(10);
    }
    
}
