package si.fri.prpo.simplejdbcsample.jdbc;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UporabnikDaoImpl implements BaseDao {

    private static       UporabnikDaoImpl instance;
    private static final Logger           log = Logger.getLogger(UporabnikDaoImpl.class.getName());

    private Connection connection;

    public static UporabnikDaoImpl getInstance() {

        if (instance == null) {
            instance = new UporabnikDaoImpl();
        }

        return instance;
    }

    public UporabnikDaoImpl() {
        connection = getConnection();
    }

    @Override
    public Connection getConnection() {
        try {
            InitialContext initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("jdbc/SimpleJdbcDS");
            return ds.getConnection();
        }
        catch (Exception e) {
            log.severe("Cannot get connection: " + e.toString());
        }
        return null;
    }

    @Override
    public Entiteta vrni(int id) {
        // iskanje uporabnika
        PreparedStatement ps = null;

        try {
            String sql = "SELECT * FROM uporabnik WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getUporabnikFromRS(rs);
            }
            else {
                log.info("Uporabnik ne obstaja");
            }

        }
        catch (SQLException e) {
            log.severe(e.toString());
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return null;
    }

    @Override
    public void vstavi(Entiteta ent) {
        // vstavljanje uporabnika
        PreparedStatement ps = null;

        try {
            if (ent instanceof Uporabnik uporabnik) {
                String sql = "INSERT INTO uporabnik (ime, priimek, uporabniskoIme) VALUES (?, ?, ?)";
                ps = connection.prepareStatement(sql);

                // Set the values for the placeholders in the SQL query
                ps.setString(1, uporabnik.getIme());
                ps.setString(2, uporabnik.getPriimek());
                ps.setString(3, uporabnik.getUporabniskoIme());

                // Execute the SQL query to insert the entity
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    log.info("Entity inserted successfully.");
                } else {
                    log.info("Entity insertion failed.");
                }
            }
        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }


    @Override
    public void odstrani(int id) {
        // odstranjevanje uporabnika
        PreparedStatement ps = null;

        try {
            String sql = "DELETE FROM uporabnik WHERE id = ?";
            ps = connection.prepareStatement(sql);

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Entity with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No entity found with ID " + id);
            }

        }
        catch (SQLException e) {
            log.severe(e.toString());
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public void posodobi(Entiteta ent) {
        // posodabljanje uporabnika
        PreparedStatement ps = null;

        try {
            Uporabnik uporabnik = (Uporabnik) ent;
            String sql = "UPDATE uporabnik SET ime = ?, priimek = ?, uporabniskoime = ? WHERE id = ?";
            ps = connection.prepareStatement(sql);

            ps.setString(1, uporabnik.getIme());
            ps.setString(2, uporabnik.getPriimek());
            ps.setString(3, uporabnik.getUporabniskoIme());
            ps.setInt(4, uporabnik.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                log.info("Uporabnik uspešno posodobljen.");
            } else {
                log.info("Posodobitev uporabnika ni uspela.");
            }

        }
        catch (SQLException e) {
            log.severe(e.toString());
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }

    }

    @Override
    public List<Entiteta> vrniVse() {

        List<Entiteta> entitete = new ArrayList<Entiteta>();
        Statement st = null;

        try {
            st = connection.createStatement();
            String sql = "SELECT * FROM uporabnik";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                entitete.add(getUporabnikFromRS(rs));
            }

        }
        catch (SQLException e) {
            log.severe(e.toString());
        }
        finally {
            if (st != null) {
                try {
                    st.close();
                }
                catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return entitete;
    }

    private static Uporabnik getUporabnikFromRS(ResultSet rs) throws SQLException {

        String ime = rs.getString("ime");
        String priimek = rs.getString("priimek");
        String uporabniskoIme = rs.getString("uporabniskoime");
        return new Uporabnik(ime, priimek, uporabniskoIme);

    }
}
