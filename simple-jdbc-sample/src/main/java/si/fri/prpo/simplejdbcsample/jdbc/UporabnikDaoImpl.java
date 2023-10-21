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
        //programska koda za vstavljanje uporabnikov

    }

    @Override
    public void odstrani(int id) {
        //programska koda za odstranjevanje uporabnikov
    }

    @Override
    public void posodobi(Entiteta ent) {
        //programska koda za posodabljanje uporabnikov

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
