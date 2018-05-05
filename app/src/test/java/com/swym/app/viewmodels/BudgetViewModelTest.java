package com.swym.app.viewmodels;

import com.swym.app.data.TransactionType;
import com.swym.app.viewmodels.main.BudgetViewModel;

import org.junit.Before;
import org.junit.Test;

import mocks.DataSourceMock;
import mocks.DepositStub;
import mocks.WithdrawalStub;

import static org.junit.Assert.assertEquals;

public class BudgetViewModelTest {
    private BudgetViewModel viewModel;
    private DataSourceMock dataSource;

    @Before
    public void setUp() {
        this.dataSource = new DataSourceMock();
        this.viewModel = new BudgetViewModel(0.00, 0.00, 250.00, this.dataSource);
    }

    @Test
    public void BudgetViewModel_AddDeposit_ShouldAdjustBalance() {
        this.viewModel.budget = 250;
        this.dataSource.setBudgetGoal(250);
        assertEquals(250.0, this.viewModel.budget, 0.0);
        assertEquals(250.0, this.viewModel.getBudgetGoal(), 0.0);
        assertEquals(0.0, this.viewModel.balance, 0.0);
        this.viewModel.addDeposit(new DepositStub("deposit1", 40.0));
        assertEquals(250.0, this.viewModel.budget, 0.0);
        assertEquals(250.0, this.viewModel.getBudgetGoal(), 0.0);
        assertEquals(40.0, this.viewModel.balance, 0.0);
    }

    @Test
    public void BudgetViewModel_AddWithdrawal_ShouldAdjustBalanceAndBudget() {
        this.viewModel.budget = 250;
        this.dataSource.setBudgetGoal(250);
        assertEquals(250.0, this.viewModel.budget, 0.0);
        assertEquals(250.0, this.viewModel.getBudgetGoal(), 0.0);
        assertEquals(0.0, this.viewModel.balance, 0.0);
        this.viewModel.addWithdrawal(new WithdrawalStub("withdrawal1", 40.0));
        assertEquals(210.0, this.viewModel.budget, 0.0);
        assertEquals(250.0, this.viewModel.getBudgetGoal(), 0.0);
        assertEquals(-40.0, this.viewModel.balance, 0.0);
    }

    @Test
    public void BudgetViewModel_InitializeWithExistingTransactions_ShouldAdjustBalanceAndBudget() {
        this.addStuffToDataSource();
        this.viewModel = new BudgetViewModel(145.00, 1145.00, 250.0, this.dataSource);
        assertEquals(145.0, this.viewModel.budget, 0.0);
        assertEquals(250.0, this.viewModel.getBudgetGoal(), 0.0);
        assertEquals(1145.0, this.viewModel.balance, 0.0);
    }

    @Test
    public void BudgetViewModel_InitializeWithExistingEverything_ShouldJustSetValues() {
        this.addStuffToDataSource();
        this.viewModel = new BudgetViewModel(400.00, 9000.0, 550.0, this.dataSource);
        assertEquals(400.0, this.viewModel.budget, 0.0);
        assertEquals(550.0, this.viewModel.getBudgetGoal(), 0.0);
        assertEquals(9000.0, this.viewModel.balance, 0.0);
    }

    @Test
    public void BudgetViewModel_UpdateBudget_ShouldAdjustBalanceAndBudget() {
        this.addStuffToDataSource();
        this.viewModel = new BudgetViewModel(400.00, 9000.0, 550.0, this.dataSource);
        assertEquals(400.0, this.viewModel.budget, 0.0);
        assertEquals(550.0, this.viewModel.getBudgetGoal(), 0.0);
        assertEquals(9000.0, this.viewModel.balance, 0.0);
        this.viewModel.updateBudget(250.0);
        assertEquals(100.0, this.viewModel.budget, 0.0);
        assertEquals(250.0, this.viewModel.getBudgetGoal(), 0.0);
        assertEquals(9000.0, this.viewModel.balance, 0.0);
    }

    private void addStuffToDataSource() {
        this.dataSource.createTransaction("d1", 1250.0, "", 0, TransactionType.DEPOSIT, "");
        this.dataSource.createTransaction("w1", 100.0, "", 0, TransactionType.WITHDRAWAL, "");
        this.dataSource.createTransaction("w2", 5.0, "", 0, TransactionType.WITHDRAWAL, "");
    }
}
