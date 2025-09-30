package ru.otus.atm;

public class ATMDemo {
    public static void main(String[] args) throws IllegalAccessException {
        ATM atm = new ATM();

        System.out.println("Пополнение банкомата:");
        atm.deposit(Banknote.RUB_100, 100);
        atm.deposit(Banknote.RUB_200, 100);
        atm.deposit(Banknote.RUB_500, 100);
        atm.deposit(Banknote.RUB_1000, 100);
        atm.deposit(Banknote.RUB_2000, 100);
        atm.deposit(Banknote.RUB_5000, 100);
        System.out.println("Общий баланс: " + atm.getBalance() + " руб. \n");

        System.out.println("===========================");
        atm.getCells().forEach((banknote, cell) -> {
            if (cell.getCount() > 0) {
                System.out.println(
                        banknote.getNominal() + " руб. x " + cell.getCount() + " = " + cell.getAmount() + " руб.");
            }
        });
        System.out.println("===========================\n");

        System.out.println("Снятие 8800 рублей:");
        atm.withdraw(8800);

        System.out.println("\n====== Остаток ======\n");
        atm.getCells().forEach((banknote, cell) -> {
            if (cell.getCount() > 0) {
                System.out.println(
                        banknote.getNominal() + " руб. x " + cell.getCount() + " = " + cell.getAmount() + " руб.");
            }
        });

        System.out.println("Попытка снять 150 рублей:");
        try {
            atm.withdraw(150);
        } catch (ATMException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
