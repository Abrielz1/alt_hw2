import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Converter {
    HashMap<Integer, ArrayList<MonthData>>monthToYearData = new HashMap<>();//сюда идут траты и доходы из файлов месяцев

    ArrayList<CountMonth> countMonth = new ArrayList<>(); //сюда идут попарно доход\расход по- месяцам 3шт

    ArrayList<YearData> year = new ArrayList<>(); //тут порубанный год, содержащий месяцы

    ArrayList<CountYear> yearCount = new ArrayList<>(); //сюда идут по-парно доход\расход помесячно за год
    //Тут проблема, он размером 0


    void convYear() { // рубим год на части
        String content = readFileContentsOrNull("resources/y.2021.csv");
        String[] lines = content.split("\r?\n");
        year = new ArrayList<>();

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");
            int month = Integer.parseInt(parts[0]);             // собственно то, что пихаем
            int amount = Integer.parseInt(parts[1]);        // собственно то, что пихаем
            boolean isExpense = Boolean.parseBoolean(parts[2]); // собственно то, что пихаем
            year.add(new YearData(month, amount, isExpense)); //пихаем результат в year
        }

    }

    public void converterMonth(){        // рубим месяцы на части...
        ArrayList<MonthData>months;
        for (int i = 1; i < 4; i++) {
            String content = readFileContentsOrNull("resources/m.20210" + i + ".csv");
            String[] lines = content.split("\r?\n");
            months = new ArrayList<>();
            for(int j = 1; j < lines.length; j++){
                String line = lines[j];
                String[] parts = line.split(",");
                String itemName = parts[0];                         // собственно то, что пихаем
                boolean isExpense = Boolean.parseBoolean(parts[1]); // собственно то, что пихаем
                int quantity = Integer.parseInt(parts[2]);          // собственно то, что пихаем
                int sumOfOne = Integer.parseInt(parts[3]);      // собственно то, что пихаем
                MonthData monthData = new MonthData(itemName, isExpense, quantity, sumOfOne);
                months.add(monthData);
            }
            monthToYearData.put(i, months); //пихаем результат в monthToYearData
        }
    }

    public void hlamTest(){ // Тут порубанное сортируем по парно Доход и Расход и пихаем в список
        //     countMonth = new ArrayList<>();
        for (Integer data : monthToYearData.keySet()) { // Тут потрошим
            int incomeMonth = 0;    //временные переменные по итерационно получают значения произведения количества на цену
            int expensesMonth = 0;  //временные переменные по итерационно получают значения произведения количества на цену
            for (MonthData set : monthToYearData.get(data)) { // Тут потрошим и выдёргиваем
                if (set.isExpense) { //фильтруем доход или расход по типу переменной isExpense, true это расход
                    expensesMonth += set.quantity * set.sumOfOne; // сортируем расходы
                } else {
                    incomeMonth += set.quantity * set.sumOfOne; // Сортируем доходы
                }
            }
            countMonth.add(new CountMonth(incomeMonth, expensesMonth)); // По-парно пихаем в список
        }


        int expenses = 0;   //временные переменные по итерационно получают значения произведения количества на цену
        int income = 0;     //временные переменные по итерационно получают значения произведения количества на цену
        for (YearData j : year) { // Потрошим

            if (!j.isExpense) { //фильтруем доход или расход по типу переменной isExpense, true это расход
                income = j.amount; // Сортируем доходы
            } else {
                expenses = j.amount; // сортируем расходы
            }

            if (!(income == 0) && !(expenses == 0)) { //сортируем, так, что бы идущий в паре с расходом или доходом 0, откинулся и добавился, только расход и доход по-парно в список.

                yearCount.add(new CountYear(income, expenses)); //Добавляем по-парно в список доход\расход, за каждый месяц

                // при попытке добавится падает


            }
        }
    }


  /*  public void numTest(){
        countMonth = new ArrayList<>();
        for(Integer data : monthToYearData.keySet()) {
            int incomeMonth = 0;
            int expensesMonth = 0;
            for(MonthData set : monthToYearData.get(data)) {
                if (set.isExpense) {
                    expensesMonth += set.quantity * set.sumOfOne;
                } else {
                    incomeMonth += set.quantity * set.sumOfOne;
                }
            }
            countMonth.add(new CountMonth(incomeMonth, expensesMonth));
        }
        countYear = new ArrayList<CountYear>();
        for(YearData i : year) {
            int incomeYear = 0;
            int expensesYear= 0;
            for(YearData j : year) {

                if(j.month.equals(i.month) && !j.isExpense) { //Здесь сравниваются 2 строки по-этому я затрудняюсь найти решение
                    incomeYear = j.amount;
                } else if (j.month.equals(i.month) && j.isExpense) {
                    expensesYear = j.amount;
                }
            }
            countYear.add(new CountYear(incomeYear, expensesYear));
        }
        for(int i = 0; i < countYear.size(); i++) { //Данный костыль выкидывает лишние строки, которые дублируются.
            if(i%1 == 0) countYear.remove(i);   //оно работает.
        }

    }
   */

    //     incomeY = 0;
    //    expensesY = 0;
    //  step = new ArrayList<>();
    //      int step = incomeY;
    //    int stepin = expensesY;
  /*  void yearlyReport() {
        countMonth = new ArrayList<>();
        for (Integer data : monthToYearData.keySet()) {
            int incomeMonth = 0;
            int expensesMonth = 0;
            for (MonthData set : monthToYearData.get(data)) {
                if (set.isExpense) {
                    expensesMonth += set.quantity * set.sumOfOne;
                } else {
                    incomeMonth += set.quantity * set.sumOfOne;
                }
            }
            countMonth.add(new CountMonth(incomeMonth, expensesMonth));
        }

        int[] step = new int[2];
        int incomeY = 0;
        int expensesY = 0;
        for (YearData j : year) {

            if (!j.isExpense) {
                incomeY = j.amount;
            } else {
                expensesY = j.amount;
            }
            step[0] = incomeY;
            step[1] = expensesY;
            if (!(step[0] == 0) && !(step[1] == 0)) {
                countYear.add(step);
                incomeY = 0;
                expensesY = 0;
                step = new int[2];
           yearlyReport()
            }
        }
    }
*/
    void monthDataPrint() { //Печатаем максимальные доходы - расходы за каждый месяц
        int a = 1; // стартовый месяц, итератор
        for (Integer values : monthToYearData.keySet()) {   // потрошим
            //Те же временные переменные
            int tempProfit = 0;
            int tempExpense = 0;
            int finExpense = 0;
            int finProfit = 0;
            String Profit = "";
            String Expense = "";
            for (MonthData  content  : monthToYearData.get(values)) {   // потрошим

                if (content.isExpense) { //фильтруем
                    tempExpense = content.quantity * content.sumOfOne;  //вычисляем
                }  else { tempProfit = content.quantity * content.sumOfOne; }   //вычисляем
                if (finExpense < tempExpense) { //ищем максимальное значение расхода
                    finExpense = tempExpense;   //присваиваем максимальное значение
                    Expense = content.itemName; //присваиваем название
                }
                if (finProfit < tempProfit) {   //ищем максимальное значение дохода
                    finProfit = tempProfit;     //присваиваем максимальное значение
                    Profit = content.itemName; //присваиваем название
                }
            }
            System.out.println("Максимальные доходы за " + a + " месяц " + finProfit + " " + Profit);
            System.out.println("Максимальные расходы за " + a + " месяц " + finExpense+ " " + Expense);
            a++;
        }
    }

    void yearDataPrint() { //Печатаем максимальные доходы - расходы за год
        int ourProfit = 0;  //Те же временные переменные
        int a = 0;
        int b = 0;
        int ourExpense = 0;
        int profit = 0;
        int expence = 0;
        int[]sumExpense = new int[3]; // создаю массивы для расхода и дохода
        int[]sumProfit = new int[3];
        System.out.println("Отчет за 2021 г.");
        for (YearData content : year) {     // потрошим
            if (content.isExpense){     //фильтруем
                expence = (content.amount); //приравниваем
                sumExpense[a++] = expence; // приравниваем, с добавкой к индексу итерируемой переменной
            } else {
                profit = (content.amount);  //приравниваем
                sumProfit[b++] = profit;    // приравниваем, с добавкой к индексу итерируемой переменной
            }
        }
        for (int z = 1; z < 4; z++) {   //печатаю прибыль
            System.out.println("Прибыль за " + z + " месяц составил " + (sumProfit[z - 1] - sumExpense[z - 1]));

        }
        for (int k = 0; k < sumProfit.length; k++) {    //щитаю медиану
            ourProfit+=sumProfit[k];
        }

        for (int h = 0; h < sumExpense.length; h++) {   //щитаю медиану
            ourExpense += sumExpense[h];
        }
        System.out.println("Медианный доход за все месяцы " + ourProfit/12);
        System.out.println("Медианный расход за все месяцы " + ourExpense/12);

    }

    void grandFinal() { //Тут сверяем счёты

        if ((yearCount.isEmpty())) { //проверяем пуст или нет список
            System.out.println("Данные отсутствуют");   //выдаем юзеру, что он забыл считать данные из п1 и п2 в меню
        } else System.out.println("Данные присутствуют"); //сообщаем, что огн молодец
        for (int i = 0; i< yearCount .size(); i++) { //потрошим
            System.out.println(yearCount.get(i).expenses + yearCount.get(i).income); //печатаем доход и расход
            if(yearCount.get(i).expenses != countMonth.get(i).expenses || yearCount.get(i).income != countMonth.get(i).income) { //Собственно сверяем
                System.out.println("В месяце " + (i+1) + " ошибка"); //радуем, битыми данными
            } else  System.out.println("Ошибок нет" + "\n"); //сообщаем, что все ништяк
        }
    }


    //  }
    private String readFileContentsOrNull(String path)  {
        try {
            return Files.readString(Path.of(path));
        }           catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
}