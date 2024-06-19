package pack;

import java.sql.*;

public class Sample {
    public static void main(String[] args) throws SQLException {
        PostgresDriverManager driverManager = PostgresDriverManager.getInstance();
        Connection connection = driverManager.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = null;
        ResultSet prepareResultSet = null;

        try{
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE id > ? and id < ?");
            preparedStatement.setInt(1,1);
            preparedStatement.setInt(2,10);

            prepareResultSet = preparedStatement.executeQuery();

//            while (prepareResultSet.next()){
//                System.out.print(" " + prepareResultSet.getInt("id"));
//                System.out.print(" " + prepareResultSet.getString("name"));
//                System.out.print(" " + prepareResultSet.getString("surname"));
//                System.out.print(" " + prepareResultSet.getInt("age"));
//                System.out.println(" " + prepareResultSet.getString("passport_number"));
//            }


//            preparedStatement = connection.prepareStatement("INSERT INTO person VALUES(10,'Oleg','M',12,'hb2234')");
//            preparedStatement.executeUpdate();

            connection.commit();
        }catch (Exception e){
            connection.rollback();
            e.printStackTrace();
            System.out.println("Exception");
        }finally {
            if(connection != null){
                connection.close();
            }
        }
    }
}
