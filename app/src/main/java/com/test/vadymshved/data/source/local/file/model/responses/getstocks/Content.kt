package com.test.vadymshved.data.source.local.file.model.responses.getstocks

import com.test.vadymshved.data.source.local.file.model.QuoteSymbol

data class Content(
    val quoteSymbols: List<QuoteSymbol>
)