package com.swym.app.viewmodels;

import junit.framework.TestCase;

public class BudgetViewModelTest extends TestCase {
    private BudgetViewModel viewModel;

    public void setUp(){
        viewModel = new BudgetViewModel(0.00, 0.00, 0.00);
    }
}
