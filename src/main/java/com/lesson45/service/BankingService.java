package com.lesson45.service;

import com.lesson45.dto.TransferCardToCardDTO;
import com.lesson45.model.Card;
import com.lesson45.model.Client;
import com.lesson45.postgresDriverManager.PostgresDriverManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BankingService {
    private PostgresDriverManager postgresDriverManager;

    public Client getUserById(int id) throws SQLException {
        String sql ="SELECT clients.client_id ,card_id, name ,balance, card_number FROM clients JOIN cards ON clients.client_id = cards.client_id WHERE clients.client_id = ?;";

        try (Connection connection = postgresDriverManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Client client = null;
            List<Card> cards = new ArrayList<>();
            while (resultSet.next()) {
                if (client == null) {
                    client = new Client();
                    client.setId(resultSet.getInt("client_id"));
                    client.setName(resultSet.getString("name"));
                }

                Card card = new Card();
                card.setId(resultSet.getInt("card_id"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setBalance(resultSet.getBigDecimal("balance"));
                cards.add(card);
            }
            if (client != null) {
                client.setCards(cards);
                return client;
            }else {
                return null;
            }
        }
    }

    public void transfer(TransferCardToCardDTO dto) throws SQLException {
        Connection connection = null;
        try {
            connection = postgresDriverManager.getConnection();
            connection.setAutoCommit(false);

            //Получаем карту клиента по ID клиента и ID карты
            String sqlCheckBalance = "SELECT balance FROM cards WHERE client_id = ? AND card_number = ?";
            PreparedStatement preparedStatementCheckBalance = connection.prepareStatement(sqlCheckBalance);
            preparedStatementCheckBalance.setInt(1, dto.getClientId());
            preparedStatementCheckBalance.setString(2, dto.getCardFrom().getCardNumber());

            ResultSet resultSet = preparedStatementCheckBalance.executeQuery();

            if (!resultSet.next()){
                throw new SQLException("Карта не найдена");
            }

            BigDecimal balanceFrom = resultSet.getBigDecimal("balance");
            BigDecimal transferAmount = dto.getAmount();

            //Отнять баланс карты отправителя
            BigDecimal newBalanceCard = balanceFrom.subtract(transferAmount);

            String sqlUpdateBalanceCard = "UPDATE cards SET balance = ? WHERE client_id = ? AND card_number = ?";
            PreparedStatement preparedStatementUpdateBalance = connection.prepareStatement(sqlUpdateBalanceCard);
            preparedStatementUpdateBalance.setBigDecimal(1, newBalanceCard);
            preparedStatementUpdateBalance.setInt(2, dto.getClientId());
            preparedStatementUpdateBalance.setString(3, dto.getCardFrom().getCardNumber());
            preparedStatementUpdateBalance.executeUpdate();


            //Проверяю существует ли такая карта
            String sqlCheckCardTo = "SELECT balance FROM cards WHERE card_number = ?";
            PreparedStatement preparedStatementCheckCardTo = connection.prepareStatement(sqlCheckCardTo);
            preparedStatementCheckCardTo.setString(1, dto.getCardTo());
            ResultSet resultSetTo = preparedStatementCheckCardTo.executeQuery();

            if (!resultSetTo.next()) {
                throw new SQLException("Карта получателя не найдена");
            }

            //Пополним карту получателя
            String sqlUpdateTo = "UPDATE cards SET balance = balance + ? WHERE card_number = ?";
            PreparedStatement preparedStatementUpdateTo = connection.prepareStatement(sqlUpdateTo);
            preparedStatementUpdateTo.setBigDecimal(1, transferAmount);
            preparedStatementUpdateTo.setString(2, dto.getCardTo());

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Ошибка");
            }
            throw e;
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Autowired
    public void setPostgresDriverManager(PostgresDriverManager postgresDriverManager) {
        this.postgresDriverManager = postgresDriverManager;
    }



}
