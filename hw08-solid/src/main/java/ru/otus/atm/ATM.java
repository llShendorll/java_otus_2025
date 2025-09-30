package ru.otus.atm;

import java.util.*;

public class ATM implements ATMInterface {

    private final Map<Banknote, Cell> cells;

    public Map<Banknote, Cell> getCells() {
        return cells;
    }

    public ATM() {
        this.cells = new TreeMap<>(Comparator.comparingInt(Banknote::getNominal).reversed());

        for (Banknote banknote : Banknote.values()) {
            this.cells.put(banknote, new Cell(banknote));
        }
    }

    @Override
    public void deposit(Banknote banknote, int count) throws IllegalAccessException {
        if (count <= 0) {
            throw new IllegalArgumentException("Количество банкнот должно быть положительным");
        }

        Cell cell = cells.get(banknote);
        cell.add(count);
    }

    @Override
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new ATMException("Сумма должна быть положительной");
        }

        if (amount > getBalance()) {
            throw new ATMException("Недостаточно средств в банкомате");
        }

        Map<Banknote, Integer> banknotes = calculateBanknotes(amount);

        if (banknotes == null) {
            throw new ATMException("Невозможно выдать запрошенную сумму имеющимися банкнотами");
        }

        for (Map.Entry<Banknote, Integer> entry : banknotes.entrySet()) {
            Cell cell = cells.get(entry.getKey());
            cell.remove(entry.getValue());
        }

        System.out.println("Выдано банкнот:");
        banknotes.forEach((banknote, count) -> System.out.println(banknote.getNominal() + " руб. x " + count));
    }

    private Map<Banknote, Integer> calculateBanknotes(int amount) {
        Map<Banknote, Integer> result = new LinkedHashMap<>();
        int remainingAmount = amount;

        for (Map.Entry<Banknote, Cell> entry : cells.entrySet()) {
            Banknote banknote = entry.getKey();
            Cell cell = entry.getValue();

            int nominal = banknote.getNominal();
            int availableCount = cell.getCount();

            if (availableCount > 0 && remainingAmount >= nominal) {
                int neededCount = Math.min(remainingAmount / nominal, availableCount);
                if (neededCount > 0) {
                    result.put(banknote, neededCount);
                    remainingAmount -= neededCount * nominal;
                }
            }

            if (remainingAmount == 0) {
                break;
            }
        }

        if (remainingAmount != 0) {
            return null; // Не получилось собрать точную сумму
        }

        return result;
    }

    @Override
    public int getBalance() {
        return cells.values().stream().mapToInt(Cell::getAmount).sum();
    }
}
