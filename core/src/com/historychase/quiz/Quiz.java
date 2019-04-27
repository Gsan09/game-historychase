package com.historychase.quiz;

import java.util.ArrayList;
import java.util.Collections;

public class Quiz extends ArrayList<Question> implements QuizRuntime{

    public Quiz() {
        Question question = new Question("When was battle of binakayan fought?");
        question.makeChoice("November 9-11, 1896",true);
        question.makeChoice("November 4-7 1896");
        this.add(question);

        question = new Question("Who was killed by Gen. Pascual Alvarez of the Sangguniang","Magdiwang in “bulwagan” of the Noveleta Tribunal?");
        question.makeChoice("Guardia Civil Capt. Antonio Robledo");
        question.makeChoice("Guardia Civil Capt. Antonio Rebolledo",true);
        this.add(question);

        question = new Question("The shore of the Cañacao Bay near the former Cavite Royal","Arsenal was where the province's patron saint","an icon known as?");
        question.makeChoice("Our lady of Lourdes");
        question.makeChoice("Lady of Solitude of Porta Vaga",true);
        this.add(question);

        question = new Question("Naval Station Sangley Point was a communication and","hospital facility of the?");
        question.makeChoice("Philippine Navy");
        question.makeChoice("United States Navy",true);
        this.add(question);

        question = new Question("Tejeros Convention Site meeting was held on?");
        question.makeChoice("March 22, 1897",true);
        question.makeChoice("May 22, 1897");
        this.add(question);

        question = new Question("Gen. Emilio Aguinaldos revolutionary army laid siege on","the Imus Church (now Cathedral) to capture the friars but","found to have fled to the recollect Estate House","after the capture of the Tribunal of Kawit on?");
        question.makeChoice("August 31, 1896",true);
        question.makeChoice("August 21, 1896");
        this.add(question);

        question = new Question("A great son of a Caviteño","the composer of the Philippine National Anthem");
        question.makeChoice("Julian Felipe",true);
        question.makeChoice("Jose Palma");
        this.add(question);

        question = new Question("The trial of Andres Bonifacio happened on?");
        question.makeChoice("1897",true);
        question.makeChoice("1889");
        this.add(question);

        question = new Question("Cavite City Hall is Located in pre-World War ll","site of Dreamland Cabaret and?");
        question.makeChoice("Pantalan de Bangco");
        question.makeChoice("the Pantalan de Yangco",true);
        this.add(question);

        question = new Question("Inside this Dasmarinas Catholic Church, now renovated","Hundreds of Filipino families were killed by Spaniards","during the Lachambre offensive to recover lost territory in late","August, 1897. The event was known as?");
        question.makeChoice("Battle of Perez- Dasmarinas",true);
        question.makeChoice("Battle of Dasmarinas");
        this.add(question);

        question = new Question("Gen. Emilio Aguinaldo appointed","General Baldomero Aguinaldo as the Commanding officer of?");
        question.makeChoice("Southern Luzon Provinces",true);
        question.makeChoice("Western Luzon Provinces");
        this.add(question);

        question = new Question("Well remembered as the Co-founder of the KKK");
        question.makeChoice("Don Ladislao Diwa",true);
        question.makeChoice("Don Severino De Las Alas");
        this.add(question);

        question = new Question("Aguinaldo ancestral home where Gen. Emilio Aguinaldo","proclaimed Philippine Independence from Spain on?");
        question.makeChoice("June 12, 1898",true);
        question.makeChoice("June 12, 1989");
        this.add(question);

        question = new Question("It is where the senior band members rehearsed","the national anthem Marcha Filipina before it was played","during the declaration of the Philippine Independence.");
        question.makeChoice("General Trias Municipal and old Church",true);
        question.makeChoice("Cavite City Municipal and old church");
        this.add(question);

        question = new Question("Due to the strategic location of Corregidor Concrete","emplacements and bomb-proof shelters were constructed"," and trails and roads were laid out on the island.","This was a planned called?");
        question.makeChoice("Harbor Defense of Cavite",true);
        question.makeChoice("Harbor Defenses of Manila and Subic bay");
        this.add(question);
    }

    @Override
    public void initQuiz() {
        Collections.shuffle(this);
        for(Question question : this)
            question.initQuiz();
        System.out.println(this);
    }

    @Override
    public String toString() {
        String stringVal = "";
        stringVal += "--------------------------------------------------------";
        stringVal += "QUIZ";
        stringVal += "--------------------------------------------------------";
        for (Question question: this)
            stringVal += "\n\t" + question;

        return stringVal;
    }
}
