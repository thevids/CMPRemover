package no.moller.carvaluation.session.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import no.moller.carvaluation.session.domain.Option;
import no.moller.commons.param.impl.CarOptionCode;
import no.moller.commons.param.impl.CarOptionName;
import no.moller.commons.util.jdbc.UtilRowMapper;

/**
 * Maps a row in <code>MWIN.CAR_VALUATION_CAR_OPTION</code> to an instance of {@link Option}.
 */
public class CarOptionDataMapper extends UtilRowMapper<Option> {

    @Override
    public Option mapRow(final ResultSet rs, final int rowNum) throws SQLException {

        return new Option(new CarOptionCode(rs.getString("OPTION_CODE")),
                           new CarOptionName(rs.getString("NAME")));
    }
}
