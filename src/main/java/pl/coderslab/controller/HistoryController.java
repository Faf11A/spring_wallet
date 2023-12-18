package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.dao.TransactionDao;
import pl.coderslab.model.Transaction;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HistoryController {

    private final TransactionDao transactionDao;

    @Autowired
    public HistoryController(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @GetMapping("/history")
    public String getTransactionHistory(Model model,
                                        @RequestParam(name = "transactionType", required = false, defaultValue = "all") String transactionType,
                                        @RequestParam(name = "sortBy", required = false, defaultValue = "date") String sortBy,
                                        @RequestParam(name = "sortOrder", required = false, defaultValue = "desc") String sortOrder) {

        List<Transaction> transactions = transactionDao.getAllTransactions();

        // Применение фильтрации по типу транзакции
        if (!"all".equals(transactionType)) {
            transactions = transactionDao.filterByType(transactions, transactionType);
        }

        // Применение сортировки
        transactions = transactionDao.sortTransactions(transactions, sortBy, sortOrder);

        model.addAttribute("transactions", transactions);
        model.addAttribute("sortOrder", sortOrder); // передача параметра sortOrder в модель

        return "history";
    }
}
