public class MonthData {
    String itemName;     //название товара
    Boolean isExpense;   //Обозначает, является ли запись тратой (TRUE) или доходом (FALSE)
    int quantity;         //количество закупленного или проданного товара
    int sumOfOne;       //стоимость одной единицы товара.

    public MonthData(String itemName, Boolean isExpense, int quantity, int sumOfOne) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }
}