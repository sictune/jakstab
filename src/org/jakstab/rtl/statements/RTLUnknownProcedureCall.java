/*
 * RTLUnknownProcedureCall.java - This file is part of the Jakstab project.
 * Copyright 2007-2013 Johannes Kinder <jk@jakstab.org>
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, see <http://www.gnu.org/licenses/>.
 */
package org.jakstab.rtl.statements;

import java.util.Collections;
import java.util.Set;

import org.jakstab.rtl.Context;
import org.jakstab.rtl.expressions.*;
import org.jakstab.util.Logger;

/**
 * The fallthrough edge of an unresolved procedure call. Generated by optimistic and
 * semi-optimistic state transformer factories.
 * 
 * @author Johannes Kinder
 */
public class RTLUnknownProcedureCall extends AbstractRTLStatement implements
		RTLStatement {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(RTLUnknownProcedureCall.class);
	
	private static final SetOfVariables FASTCALL_USED;
	private static final SetOfVariables INTEL_ABI_DEFINED;
	static {
		FASTCALL_USED = new SetOfVariables();
		FASTCALL_USED.add(ExpressionFactory.createVariable("%eax"));
		FASTCALL_USED.add(ExpressionFactory.createVariable("%ecx"));
		FASTCALL_USED.add(ExpressionFactory.createVariable("%edx"));
		INTEL_ABI_DEFINED = new SetOfVariables();
		INTEL_ABI_DEFINED.add(ExpressionFactory.createVariable("%eax"));
		INTEL_ABI_DEFINED.add(ExpressionFactory.createVariable("%ecx"));
		INTEL_ABI_DEFINED.add(ExpressionFactory.createVariable("%edx"));
	}
	
	private final RTLGoto source;

	/**
	 * 
	 */
	public RTLUnknownProcedureCall(RTLGoto source) {
		this.source = source;
	}

	/*
	 * @see org.jakstab.rtl.statements.AbstractRTLStatement#initDefinedVariables()
	 */
	@Override
	protected SetOfVariables initDefinedVariables() {
		return INTEL_ABI_DEFINED;
	}

	/*
	 * @see org.jakstab.rtl.statements.AbstractRTLStatement#initUsedMemoryLocations()
	 */
	@Override
	protected Set<RTLMemoryLocation> initUsedMemoryLocations() {
		return Collections.emptySet();
	}

	/*
	 * @see org.jakstab.rtl.statements.AbstractRTLStatement#initUsedVariables()
	 */
	@Override
	protected SetOfVariables initUsedVariables() {
		return FASTCALL_USED;
	}

	/*
	 * @see org.jakstab.rtl.statements.RTLStatement#accept(org.jakstab.rtl.statements.StatementVisitor)
	 */
	@Override
	public <T> T accept(StatementVisitor<T> visitor) {
		return visitor.visit(this);
	}

	/*
	 * @see org.jakstab.rtl.statements.RTLStatement#evaluate(org.jakstab.rtl.Context)
	 */
	@Override
	public RTLStatement evaluate(Context context) {
		return this;
	}
	
	public RTLGoto getSource() {
		return source;
	}

	@Override
	public String toString() {
		return "return from call(" + source.getTargetExpression() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RTLUnknownProcedureCall other = (RTLUnknownProcedureCall) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

}
