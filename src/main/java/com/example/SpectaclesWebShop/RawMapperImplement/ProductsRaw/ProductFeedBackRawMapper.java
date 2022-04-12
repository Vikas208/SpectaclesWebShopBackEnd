package com.example.SpectaclesWebShop.RawMapperImplement.ProductsRaw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.example.SpectaclesWebShop.Bean.FeedBack;

import org.springframework.jdbc.core.*;

public class ProductFeedBackRawMapper implements RowMapper<FeedBack> {

       @Override
       public FeedBack mapRow(ResultSet rs, int rowNum) throws SQLException {
              FeedBack feedBack = new FeedBack();
              feedBack.setId(rs.getLong("PR_ID"));
              feedBack.setP_id(rs.getLong("P_ID"));
              feedBack.setC_id(rs.getLong("C_ID"));
              feedBack.setUser(rs.getString("MAILID"));
              feedBack.setRating(rs.getDouble("RATING"));
              feedBack.setFeedBack(rs.getString("FEEDBACK"));
              feedBack.setTime(rs.getObject("REVIEW_TIME", LocalDateTime.class));
              return feedBack;
       }

}
