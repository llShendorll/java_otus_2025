package ru.otus.atm;

public interface ATMInterface {
    /**
     * Внести банкноты в банкомат.
     * @param banknote - номинал
     * @param count - количество
     */
    void deposit(Banknote banknote, int count) throws IllegalAccessException;

    /**
     * Выдать указанную сумму
     * @param amount - сумма к выдаче
     */
    void withdraw(int amount);

    /**
     * Узнать баланс
     */
    int getBalance();
}
