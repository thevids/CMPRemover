package no.moller.carvaluation.session.dao;

import java.util.List;

import javax.annotation.Nullable;

import no.moller.carvaluation.session.domain.Option;
import no.moller.carvaluation.session.domain.Session;
import no.moller.carvaluation.session.rowmapper.CarOptionDataMapper;
import no.moller.carvaluation.session.rowmapper.SessionFlatDataMapper;
import no.moller.carvaluation.session.rowmapper.SessionMapper;
import no.moller.commons.dao.DaoHelper;
import no.moller.commons.param.impl.SessionId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * DAO for {@link Session}.
 */
public class SessionDao {

    /** DBUnit can not have really long table-names due to Excel limits, so this must be manipulated in test. */
    private static String MWIN_CAR_VALUATION_PRECALC = "MWIN.CAR_VALUATION_PRECALCULATION";

    @Autowired
    private NamedParameterJdbcTemplate mwinNamedTemplate;

    @Nullable
    public Session readSessionOnly(final SessionId id) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Session.PARAM_ID, id.getValue());

        final String query =
                "SELECT" +
                        " sess.ID," +
                        " sess.COUNTRY_CODE," +
                        " sess.DEALER_NO," +
                        " sess.CURRENT_STATE," +
                        " sess.SELLER_NO" +
                        " FROM MWIN.CAR_VALUATION_SESSION sess" +
                        " WHERE sess.ID = :" + Session.PARAM_ID;

        final List<Session> factoryCarOrders = mwinNamedTemplate.query(query, params, new SessionMapper());

        return DaoHelper.getSingleResult(factoryCarOrders);
    }

    /**
     * Reads a Session with a lot of related data and all one-to-many options (ekstrautstyr)
     * for the car.
     *
     * @param id Unique sessionId
     * @return Session domain object with related domain objects
     */
    @Nullable
    public Session readSessionWithRelations(final SessionId id) {
        final List<Option> options = this.readOptionsFromCarWithSessionId(id);

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Session.PARAM_ID, id.getValue());

        final String query =
                "SELECT "
                + "sess.ID, "
                + "sess.COUNTRY_CODE, "
                + "sess.DEALER_NO, "
                + "sess.CURRENT_STATE, "
                + "sess.SELLER_NO, "
                + "calc.ADVERTISED_SALES_PRICE, "
                + "precalc.ANNUAL_DUTY_PAID, "
                + "precalc.VEHICLE_CONTROL_DATE, "
                + "precalc.RELEVANT_FOR_TRADEIN, "
                + "data.TYPE, "
                + "data.REG_NO, "
                + "data.VIN, "
                + "data.COLOUR, "
                + "data.COLOUR_NAME, "
                + "data.COLOUR_INTERIOR, "
                + "data.NO_OF_SEATS, "
                + "data.NO_OF_DOORS, "
                + "data.REGISTRATION_DATE_ORIGINAL_COUNTRY, "
                + "data.REGISTRATION_DATE, "
                + "data.GEAR_TYPE, "
                + "data.DRIVING_WHEELS, "
                + "data.BRAND, "
                + "data.MODEL, "
                + "data.POWER_IN_HP, "
                + "data.ENGINE_VOLUME_IN_LITRE, "
                + "vgroup.CARWEB_DEFINITION AS CLASSIFICATION, "
                + "fuel.TEXT, "
                + "body.CARWEB_DEFINITION AS BODYTYPE, "
                + "value.USED_IMPORT, "
                + "value.BODY_ID, "
                + "value.SERVICE_HANDBOOK, "
                + "inspect.CURRENT_MILEAGE "

                // From
                + "FROM  MWIN.CAR_VALUATION_SESSION      sess "
                +       "LEFT JOIN MWIN.CAR_VALUATION_CAR_DATA   data ON data.session_id = sess.id " +
                "        LEFT JOIN MWIN.CAR_VALUATION_CALCULATION calc ON calc.session_id = sess.id " +
                "        LEFT JOIN "+MWIN_CAR_VALUATION_PRECALC+" precalc ON precalc.session_id = sess.id " +
                "        LEFT JOIN MWIN.CAR_VALUATION_INSPECTION inspect ON inspect.session_id = sess.id " +
                "        LEFT JOIN MWIN.CAR_VALUATION value ON value.session_id = sess.id " +
                "        left join mwin.FUEL_TYPE fuel on fuel.ID = data.FUEL_TYPE_ID  " +
                "        left join mwin.VEHICLE_GROUP vgroup on vgroup.ID = data.VEHICLE_GROUP_ID " +
                "        left join mwin.CAR_VALUATION_BODY body on body.ID = data.BODY_ID "

                // Where injected with outside param
                + "WHERE sess.ID = :" + Session.PARAM_ID;

        final List<Session> factoryCarOrders = mwinNamedTemplate.query(query, params, new SessionFlatDataMapper(options));

        return DaoHelper.getSingleResult(factoryCarOrders);
    }

    @Nullable
    public List<Option> readOptionsFromCarWithSessionId(final SessionId id) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Session.PARAM_ID, id.getValue());

        final String query =
                "SELECT opt.NAME, opt.OPTION_CODE " +
                " FROM MWIN.CAR_VALUATION_CAR_OPTION opt, MWIN.CAR_VALUATION_CAR_DATA data " +
                " WHERE opt.CAR_ID = data.ID AND data.session_id = :" + Session.PARAM_ID;

        final List<Option> options = mwinNamedTemplate.query(query, params, new CarOptionDataMapper());

        return options;
    }

    /** Package scoped for use by a dao-test, in memory db. */
    void setToTableNameForHSQLDBInTestOnly() {
        MWIN_CAR_VALUATION_PRECALC = "MWIN.CAR_VALUATION_PRECALC";
    }
}
