/*
 *    ImageI/O-Ext - OpenSource Java Image translation Library
 *    http://www.geo-solutions.it/
 *    (C) 2007 - 2016, GeoSolutions
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    either version 3 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.imageio.cog;

import it.geosolutions.imageioimpl.plugins.cog.CogTileInfo;
import it.geosolutions.imageioimpl.plugins.cog.RangeBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Testing ability to build tile range metadata.
 * 
 * @author joshfix
 */
public class CogTileInfoTest extends Assert {

    @Test
    public void buildTileInfo() {
        int headerLength = 16384;
        CogTileInfo info = new CogTileInfo(headerLength);

        int tile1Index = 0;
        int tile1Offset = 10000;
        int tile1ByteLength = 100;
        info.addTileRange(tile1Index, tile1Offset, tile1ByteLength);

        // verify the header length is adjusted to not overlap with the first tile offset
        Assert.assertEquals(tile1Offset - 1, info.getHeaderSize());

        // verify the added tile range has the expected offset and length
        Assert.assertEquals(tile1Offset, info.getFirstTileOffset());
        Assert.assertEquals(tile1ByteLength, info.getFirstTileByteLength());

        // verify that given a position in the byte array, the proper tile index is returned
        Assert.assertEquals(tile1Index, info.getTileIndex(tile1Offset + (tile1ByteLength / 2)));

        // test that getting a tile range by an offset or by index results in the same TileRange object
        CogTileInfo.TileRange tileRange1 = info.getTileRange((long)(tile1Offset + (tile1ByteLength / 2)));
        CogTileInfo.TileRange tileRange2 = info.getTileRange(tile1Index);
        Assert.assertEquals(tileRange1, tileRange2);

    }
}
