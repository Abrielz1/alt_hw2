public class MonthData {
    String itemName;     //�������� ������
    Boolean isExpense;   //����������, �������� �� ������ ������ (TRUE) ��� ������� (FALSE)
    int quantity;         //���������� ������������ ��� ���������� ������
    int sumOfOne;       //��������� ����� ������� ������.

    public MonthData(String itemName, Boolean isExpense, int quantity, int sumOfOne) {
        this.itemName = itemName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }
}