package com.db.ramon.aggregate;

import com.db.ramon.domain.*;
import com.db.ramon.value.object.ItemAlsoIncludes;
import com.db.ramon.value.object.ItemExcludes;
import com.db.ramon.value.object.ItemIncludes;
import com.db.ramon.value.object.ReferenceToIsicRev4;

import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public interface NaceAggregate {
  OrderId orderId();

  Level level();

  Code code();

  Parent parent();

  Description description();

  Optional<ItemIncludes> itemIncludes();

  Optional<ItemAlsoIncludes> itemAlsoIncludes();

  Optional<Rulings> rulings();

  Optional<ItemExcludes> itemExcludes();

  ReferenceToIsicRev4 refToIsicRev4();
}
