package com.epam.sudoku.service;

import java.util.Objects;

public class CellOption {
    Condition condition;
    String identifier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellOption that = (CellOption) o;
        return getCondition() == that.getCondition() &&
                getIdentifier().equals(that.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCondition(), getIdentifier());
    }

    public Condition getCondition() {
        return condition;
    }

    public String getIdentifier() {
        return identifier;
    }

    public CellOption(Condition condition, String identifier) {
        this.condition = condition;
        this.identifier = identifier;
    }
}
