import java.util.*;         // com = месяц
public class Main {

    public static void main(String[] args) {
        Converter converterMonth = new Converter();
        System.out.println("Здравствуйте!" + "\n");
        Scanner scanner = new Scanner(System.in);

        while (true){
            Menu.startMenu();
            int command = scanner.nextInt();
            switch (command) {
                case 1 :
                    converterMonth.converterMonth();
                    break;
                case 2 :
                    converterMonth.convYear();
                    break;
                case 3 :
                    converterMonth.grandFinal();
                    break;
                case 4 :
                    converterMonth.monthDataPrint();
                    break;
                case 5 :
                    converterMonth.yearDataPrint();
                    break;

                case 0 :{
                    System.out.println("Программа завершена\n До свидания!");
                    return;
                }
                default : System.out.println("Такой команды нет\n");
                    break;
            }
        }
    }
}


