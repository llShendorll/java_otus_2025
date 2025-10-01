package ru.otus.atm;

/**
 * Ячейка для хранения банкнот одного номинала
 */
public class Cell {
    private final Banknote banknote;
    private int count;

    public Cell(Banknote banknote) {
        this.banknote = banknote;
        this.count = 0;
    }

    /**
     * Добавить банкноты в ячейку
     */
    public void add(int amount) throws IllegalAccessException {
        if (amount < 0) {
            throw new IllegalAccessException("Количество банкнот не может быть отрицательным");
        }
        count += amount;
    }

    /**
     * Получить банкноты из ячейки
     */
    public void remove(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Количество банкнот не может быть отрицательным");
        }
        if (amount > count) {
            throw new IllegalStateException("Недостаточно банкнот в ячейке");
        }
        this.count -= amount;
    }

    /**
     * Получить количество банкнот
     */
    public int getCount() {
        return count;
    }

    /**
     * Получить номинал банкноты
     */
    public Banknote getBanknote() {
        return banknote;
    }

    /**
     * Получить общую сумму в ячейке
     */
    public int getAmount() {
        return count * banknote.getNominal();
    }
}
