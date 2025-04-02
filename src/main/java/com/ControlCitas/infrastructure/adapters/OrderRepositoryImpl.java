package com.ControlCitas.infrastructure.adapters;

import com.ControlCitas.infrastructure.database.ConnectionDb;
import com.skeletonhexa.domain.entities.*;
import com.skeletonhexa.domain.repository.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    private final ConnectionDb connectionDb;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderRepositoryImpl(ConnectionDb connectionDb,
            ClientRepository clientRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {
        this.connectionDb = connectionDb;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        Connection conn = null;
        try {
            conn = connectionDb.getConexion();
            conn.setAutoCommit(false);

            // Validaciones adicionales recomendadas
            validateOrderFields(order);

            String sql = "INSERT INTO orders (id_client, id_user, order_date, id_order_status, notes, total, expected_delivery) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                // Configuración de parámetros corregida
                stmt.setString(1, order.getClient().getTaxId()); // Corregido setNString a setString
                stmt.setInt(2, order.getUser().getIdUser()); // Corregido setint a setInt
                stmt.setTimestamp(3, Timestamp.valueOf(order.getOrderDate()));
                stmt.setInt(4, order.getStatus().getIdOrderStatus());
                stmt.setString(5, order.getNotes());
                stmt.setDouble(6, order.getTotal());

                // Manejo mejorado de valores nulos
                if (order.getExpectedDelivery() != null) {
                    stmt.setTimestamp(7, Timestamp.valueOf(order.getExpectedDelivery()));
                } else {
                    stmt.setNull(7, Types.TIMESTAMP);
                }

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating order failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setIdOrder(generatedKeys.getInt(1));
                        saveOrderDetails(order, conn);
                        conn.commit();
                        return order;
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            rollbackConnection(conn);
            throw new RuntimeException("Error saving order", e);
        } finally {
            closeConnection(conn);
        }
    }

    // Métodos auxiliares recomendados
    private void validateOrderFields(Order order) {
        if (order.getClient() == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        if (order.getUser() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (order.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (order.getOrderDate() == null) {
            throw new IllegalArgumentException("Order date cannot be null");
        }
    }

    private void rollbackConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveOrderDetails(Order order, Connection conn) throws SQLException {
        String sql = "INSERT INTO order_details (id_order, id_product, quantity, unit_price, discount, subtotal) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (OrderDetail detail : order.getDetails()) {
                stmt.setInt(1, order.getIdOrder());
                stmt.setInt(2, detail.getProduct().getIdProduct());
                stmt.setInt(3, detail.getQuantity());
                stmt.setDouble(4, detail.getUnitPrice());
                stmt.setDouble(5, detail.getDiscount());
                stmt.setDouble(6, detail.getSubtotal());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public Optional<Order> findById(int id) {
        String sql = "SELECT * FROM orders WHERE id_order = ?";

        try (Connection conn = connectionDb.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Order order = mapOrder(rs);
                order.setDetails(findOrderDetails(order.getIdOrder(), conn));
                return Optional.of(order);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding order", e);
        }
    }

    @Override
    public boolean delete(int orderId) {
        String sql = "DELETE FROM orders WHERE id_order = ?";

        try (Connection conn = connectionDb.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order", e);
        }
    }

    @Override
    public List<Order> findByUserId(int userId) {
        String sql = "SELECT * FROM orders WHERE id_user = ? ORDER BY order_date DESC";

        try (Connection conn = connectionDb.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = mapOrder(rs);
                order.setDetails(findOrderDetails(order.getIdOrder(), conn));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding orders by user id", e);
        }
    }

    @Override
    public List<Order> findByUser(User user) {
        return findByUserId(user.getIdUser());
    }

    @Override
    public List<Order> findByClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        String sql = "SELECT * FROM orders WHERE id_client = ? ORDER BY order_date DESC";

        try (Connection conn = connectionDb.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, client.getIdClient());

            ResultSet rs = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();

            while (rs.next()) {
                Order order = mapOrder(rs);
                order.setDetails(findOrderDetails(order.getIdOrder(), conn));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding client orders", e);
        }
    }

    @Override
    public List<OrderStatus> getAllStatuses() {
        String sql = "SELECT * FROM order_statuses WHERE is_active = TRUE";

        try (Connection conn = connectionDb.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            List<OrderStatus> statuses = new ArrayList<>();
            while (rs.next()) {
                OrderStatus status = new OrderStatus(
                        rs.getInt("id_order_status"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getBoolean("is_active"));
                statuses.add(status);
            }
            return statuses;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting order statuses", e);
        }
    }

    @Override
    public boolean updateStatus(int orderId, OrderStatus newStatus) {
        String sql = "UPDATE orders SET id_order_status = ? WHERE id_order = ?";

        try (Connection conn = connectionDb.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newStatus.getIdOrderStatus());
            stmt.setInt(2, orderId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order status", e);
        }
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setIdOrder(rs.getInt("id_order"));
        order.setClient(clientRepository.findById((int) rs.getInt("id_client"))
                .orElseThrow(() -> new RuntimeException("Client not found")));
        order.setUser(userRepository.findById((int) rs.getInt("id_user"))
                .orElseThrow(() -> new RuntimeException("User not found")));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        order.setStatus(getStatusById(rs.getInt("id_order_status")));
        order.setNotes(rs.getString("notes"));
        order.setTotal(rs.getDouble("total"));
        order.setExpectedDelivery(
                rs.getTimestamp("expected_delivery") != null ? rs.getTimestamp("expected_delivery").toLocalDateTime()
                        : null);
        return order;
    }

    private OrderStatus getStatusById(int statusId) throws SQLException {
        String sql = "SELECT * FROM order_statuses WHERE id_order_status = ?";

        try (Connection conn = connectionDb.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, statusId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new OrderStatus(
                        rs.getInt("id_order_status"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getBoolean("is_active"));
            }
            throw new RuntimeException("Order status not found with ID: " + statusId);
        }
    }

    private List<OrderDetail> findOrderDetails(int orderId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM order_details WHERE id_order = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            List<OrderDetail> details = new ArrayList<>();
            while (rs.next()) {
                OrderDetail detail = new OrderDetail();
                detail.setIdDetail(rs.getInt("id_detail"));
                detail.setProduct(productRepository.findById(rs.getInt("id_product"))
                        .orElseThrow(() -> new RuntimeException("Product not found")));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setUnitPrice(rs.getDouble("unit_price"));
                detail.setDiscount(rs.getDouble("discount"));
                detail.setSubtotal(rs.getDouble("subtotal"));
                details.add(detail);
            }
            return details;
        }
    }
}